import rsa
import os
import base64

with open('/Users/guanyuhang/Desktop/whisper/src/private_key.pem', mode = 'rb') as privatefile:
    keydata1 = privatefile.read()
privatekey = rsa.PrivateKey.load_pkcs1(keydata1)




with open('/Users/guanyuhang/Desktop/whisper/src/ciphertxt.txt','r') as ciphertxt:
    cipher = ciphertxt.read()
crypto_msg = base64.decodebytes(cipher.encode('utf8'))
plain = rsa.decrypt(crypto_msg, privatekey)
plain_msg = plain.decode('utf8')

print(plain_msg)

with open('/Users/guanyuhang/Desktop/whisper/src/plaintxt.txt', 'w+') as f:
    f.write(plain_msg)


