import numpy as np
import pygame
import sys
import math
import socket
import time
import sys
import _pickle as pickle


###########################################
#  GUI Related Functions(You may skip over)
###########################################
BLUE = (0, 230, 235)
BLACK = (50, 50, 50)
RED = (255, 0, 0)
YELLOW = (255, 255, 0)

ROW_COUNT = 7
COLUMN_COUNT = 7


def init_gui_setting():
    global size, screen, myfont, SQUARESIZE, RADIUS, width, height
    pygame.init()
    SQUARESIZE = 100
    width = COLUMN_COUNT * SQUARESIZE
    height = (ROW_COUNT + 1) * SQUARESIZE
    size = (width, height)
    RADIUS = int(SQUARESIZE / 2 - 5)
    screen = pygame.display.set_mode(size)
    pygame.display.update()
    myfont = pygame.font.SysFont("monospace", 65)


def draw_board(board):
    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT):
            pygame.draw.rect(screen, BLUE, (c * SQUARESIZE, r * SQUARESIZE + SQUARESIZE, SQUARESIZE, SQUARESIZE))
            pygame.draw.circle(screen, BLACK, (
            int(c * SQUARESIZE + SQUARESIZE / 2), int(r * SQUARESIZE + SQUARESIZE + SQUARESIZE / 2)), RADIUS)

    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT):
            if board[r][c] == 1:
                pygame.draw.circle(screen, RED, (
                int(c * SQUARESIZE + SQUARESIZE / 2), height - int(r * SQUARESIZE + SQUARESIZE / 2)), RADIUS)
            elif board[r][c] == 2:
                pygame.draw.circle(screen, YELLOW, (
                int(c * SQUARESIZE + SQUARESIZE / 2), height - int(r * SQUARESIZE + SQUARESIZE / 2)), RADIUS)
    pygame.display.update()


def print_board(board):
    if SHOW_TEXT_BOARD:
        print(np.flip(board, 0))


##############
#  Game Logic
##############

def create_board():
    board = np.zeros((ROW_COUNT, COLUMN_COUNT))
    return board


# Drop a piece on a column, and update the matrix
def drop_piece(board, row, col, turn):
    board[row][col] = turn


# Return True if the selected column is not full
def is_valid_location(board, col):
    return board[ROW_COUNT - 1][col] == 0


# Return True all positions have been filled (i.e., end of game)
def no_more_moves(board):
    return np.count_nonzero(board) == COLUMN_COUNT * ROW_COUNT


# Place a piece into a column and update the game matrix
def do_move(board, col, turn):
    for row in range(ROW_COUNT):
        if board[row][col] == 0:
            board[row][col] = turn
            break


def get_row(board, col):
    for r in range(ROW_COUNT):
        if board[r][col] == 0:
            return r

####################################################
#  Code for handling Human Player's move
#  It returns the column mouse-selected by the user
####################################################

def player_move(board, turn):
    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

            if event.type == pygame.MOUSEMOTION:
                pygame.draw.rect(screen, BLACK, (0, 0, width, SQUARESIZE))
                posx = event.pos[0]
                print(posx)
                if turn == 1:
                    pygame.draw.circle(screen, RED, (posx, int(SQUARESIZE / 2)), RADIUS)
                else:
                    pygame.draw.circle(screen, YELLOW, (posx, int(SQUARESIZE / 2)), RADIUS)

            pygame.display.update()

            if event.type == pygame.MOUSEBUTTONDOWN:
                pygame.draw.rect(screen, BLACK, (0, 0, width, SQUARESIZE))

                posx = event.pos[0]
                col = int(math.floor(posx / SQUARESIZE))
                print(col)
                if is_valid_location(board, col):
                    return col


#############################################
# Functions for checking if one side has won
#############################################
#  Return the longest continous sequence of a player's pieces in a line
#    (if length of line is less than 4, it always return zero)
#
#  E.g., check_line([ 0, 1, 1, 1, 0, 1, 1], 1) woukd return 3
#
def check_line(line, turn):
    count = 0
    max_count = 0
    if len(line) < 4:
        return 0

    for x in line:
        if x == turn:
            count += 1
            if count >= max_count:
                max_count = count
        else:
            count = 0
    return max_count


#
# Check if eitherF side is won by checking each row, columm and diagonal in turn.
# If the player has >= 4 consecutive pieces, it has won.
#
def win(board, turn):
    # Check eack column for win
    for c in range(COLUMN_COUNT):
        col = board[:, c]
        if check_line(col, turn) >= 4:
            return True

    # Check eack row for win
    for r in range(ROW_COUNT):
        row = board[r, :]
        if check_line(row, turn) >= 4:
            return True

    # Check diagonals  [\\]
    for d in range(-4, 4):
        diag = board.diagonal(d)
        if check_line(diag, turn) >= 4:
            return True

    # Check opposite diagonals [//]
    for d in range(-4, 4):
        diag = np.flip(board, 1).diagonal(d)
        if check_line(diag, turn) >= 4:
            return True


###########################################
#   AI FUNCTIONS score funtion
###########################################

def score(board):
    score = 0

    # Check score of each column
    for c in range(COLUMN_COUNT):
        col = board[:, c]
        num0 = check_line(col, 0)
        num1 = check_line(col, 1)
        num2 = check_line(col, 2)
        if num1 == 4:
            score += 100
        if num1 == 2 and num0 == 2:
            score += 2
        elif num1 == 3 and num0 == 1:
            score += 5
        if num2 == 2 and num0 == 2:
            score += -1
        if num2 == 3 and num0 == 1:
            score += -4

    # Check score of each row
    for r in range(ROW_COUNT):
        row = board[r, :]
        num0 = check_line(row, 0)
        num1 = check_line(row, 1)
        num2 = check_line(row, 2)
        if num1 == 2 and num0 == 2:
            score += 2
        elif num1 == 3 and num0 == 1:
            score += 5
        if num2 == 2 and num0 == 2:
            score += -1
        if num2 == 3 and num0 == 1:
            score += -4

    # Check diagonols scores  [\\]
    for d in range(-4, 4):
        diag = board.diagonal(d)
        num0 = check_line(diag, 0)
        num1 = check_line(diag, 1)
        num2 = check_line(diag, 2)
        if num1 == 2 and num0 == 2:
            score += 2
        elif num1 == 3 and num0 == 1:
            score += 5
        if num2 == 2 and num0 == 2:
            score += -1
        if num2 == 3 and num0 == 1:
            score += -4

    # Check opposite diagonals scores [//]
    for d in range(-4, 4):
        diag = np.flip(board, 1).diagonal(d)
        num0 = check_line(diag, 0)
        num1 = check_line(diag, 1)
        num2 = check_line(diag, 2)
        if num1 == 2 and num0 == 2:
            score += 2
        elif num1 == 3 and num0 == 1:
            score += 5
        if num2 == 2 and num0 == 2:
            score += -1
        if num2 == 3 and num0 == 1:
            score += -4

    return score


####################
## Scoring Functions
####################

#
#  Define the score function as the sum of
#   the longest consecutive sequence of each players in all cols, rows and diagonals
#  The score is +ve for player1 and -ve for player2
#

#
#   TO DO: ADD YOUR CODE IN THE FOLLOWING FUNCTION
#

def terminal_game(board):
    return win(board, 2) or len(list_moves(board)) == 0 or win(board, 1)


# minimax algorithm
def minimax(board, switch, level, al, be):
    valid_moves = list_moves(board)
    is_terminal = terminal_game(board)
    if level == 0 or is_terminal:
        if is_terminal:
            if win(board, 2):
                return (99999999999999999, None)
            elif win(board, 1):
                return (-99999999999999999, None)
            else:  # Game is over, no more valid moves
                return (0, None)
        else:  # Depth is zero
            return (score(board), None)
    if switch:
        value = -math.inf
        column = -1
        for col in valid_moves:
            row = get_row(board, col)
            child_state = board.copy()
            drop_piece(child_state, row, col, 2)
            s_new = minimax(child_state, 0, level - 1, al, be)[0]
            if s_new > value:
                value = s_new
                column = col
            al = max(al, value)
            if al >= be:
                break
        return column, value

    else:  # Minimizing player
        value = math.inf
        column = -1
        for col in valid_moves:
            row = get_row(board, col)
            child_state = board.copy()
            drop_piece(child_state, row, col, 1)
            s_new = minimax(child_state, 1, level - 1, al, be)[0]
            if s_new < value:
                value = s_new
                column = col
            be = min(be, value)
            if al >= be:
                break
        return column, value


def list_moves(board):
    valid_locations = []
    for col in range(COLUMN_COUNT):
        if is_valid_location(board, col):
            valid_locations.append(col)
    return valid_locations


class Peer:
    def __init__(self, local_ip="127.0.0.1", local_port=8888, \
                       server_ip="127.0.0.1",  server_port=9999):
        # local ip and port
        self.local_ip = local_ip
        self.local_port = local_port
        # peer's ip and port, used to connect to peer, default peer runs on the same
        # computer(local_host:127.0.0.1) and port 9999
        self.server_ip = server_ip
        self.server_port = server_port
        # create a socket and connect it to peer
        self.sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.sock.connect((self.server_ip, self.server_port))

    def send_move(self, col):
        # send the column of current move
        self.sock.send(col.to_bytes(1, byteorder='little'))

    def recv_move(self) -> int:
        # receive the column(represent by one byte) of move of peer
        m = self.sock.recv(1)
        # since received data type is byte, we will transform it to int
        return int.from_bytes(m, byteorder='little')

    def select_peer_player(self, player):
        if player == 'human':
            # use one byte which equals 1 to represents a human player
            self.sock.send((1).to_bytes(1, byteorder='little'))
        else:
            # use one byte which equals 2 to represents a human player
            self.sock.send((2).to_bytes(1, byteorder='little'))


#main function
def play_game():
    board = create_board()
    print_board(board)
    draw_board(board)
    game_over = False
    turn = 1
    peer = Peer()

    peer.select_peer_player(player2)

    while not game_over:
        # the board is full
        if no_more_moves(board):
            game_over = True
            break;

        if turn == 1:
            # get move from mouse clicking
            col = player_move(board, turn)
            # send the move to server/peer
            peer.send_move(col)
        else:
            # get the move of peer from the connection between two player
            col = peer.recv_move()

        do_move(board, col, turn)

        if win(board, turn):
            label = myfont.render("Player {} wins!!".format(turn), 1, BLUE)
            screen.blit(label, (40, 10))
            game_over = True

        print_board(board)
        draw_board(board)
        if turn == 1:
            turn = 2
        else:
            turn = 1

    #close pywindow
    if game_over:
        time.sleep(5)
        #pygame.time.wait(50000)
        pygame.quit()

#######################
# select a peer
#######################
def select_peer():
    while True:
        peer = input("select a peer, human peer input 'human', computer peer input 'computer', input:")
        if peer == 'human':
            return 'human'
        elif peer == 'computer':
            return 'computer'
        else:
            continue



########################
#  Main
########################

# change the play type here/ Ai vs Ai or Ai vs human or human vs human
#player1 = "computer"
player1 = "human"
player2 = select_peer()
#player2 = "computer"

SHOW_TEXT_BOARD=False
# SHOW_TEXT_BOARD = True


init_gui_setting()
play_game()
pygame.quit()
sys.exit()