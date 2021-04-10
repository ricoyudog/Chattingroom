import rsa
import os
import base64
print("start")
with open('/Users/guanyuhang/Desktop/whisper/src/public_key.pem', mode = 'rb') as publicfile:
    keydata2 = publicfile.read()
publickey = rsa.PublicKey.load_pkcs1_openssl_pem(keydata2)

with open('/Users/guanyuhang/Desktop/whisper/src/input.txt') as inputfile:
    msg = inputfile.read().encode('utf8')

crypto = rsa.encrypt(msg, publickey)


crypto_msg = base64.encodebytes(crypto).decode('utf8')
print(crypto_msg)
with open('/Users/guanyuhang/Desktop/whisper/src/ciphertxt.txt', 'w+') as f:
    f.write(crypto_msg)





