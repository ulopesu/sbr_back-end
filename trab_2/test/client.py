#!/usr/bin/env python
import time
import json

from urllib import request, parse

headers = {
    "Content-Type": "application/json",
    "Accept": "application/json",
}


# CREATE CAMARA
url = "http://localhost:8080/camara"
data_json = {
    "nome": "Camara 1 - UFES",
    "loc": {
        "latitude": -20.273610030740326, 
        "longitude": -40.30586659439299
        },
    "temperatura": 16.0
}
data = json.dumps(data_json).encode("utf-16")
try:
    req = request.Request(url, data, headers, method='POST')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)



# CREATE GESTOR
url = "http://localhost:8080/gestor"
data_json = {
    "nome": "Isabel",
    "email": "isa@ufes.br",
    "telefone": "(27) 99999-9999",
    "loc": {
        "latitude": -20.312728328794147,
        "longitude": -40.2880710598846
    },
    "camara": {
        "id": 1
    }
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='POST')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)



# CREATE VACINA
url = "http://localhost:8080/vacina"
data_json = {
    "nome": "Corona VAC",
    "tempMax": 20.0,
    "tempMin": 5.0,
    "margem": 20
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='POST')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)



# CREATE LOTE
url = "http://localhost:8080/lote"
data_json = {
    "qtd": 100,
    "vacina": {
        "id": 1
    },
    "camara": {
        "id": 1
    },
    "validade": "2021-05-20"
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='POST')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)



# TESTE CAMARA DEFEITO
# CREATE CAMARA
time.sleep(1) 
url = "http://localhost:8080/camara/1"
data_json = {
    "temperatura": 8.0
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='PUT')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)


#   1
time.sleep(1)
data_json = {
    "temperatura": 18.0
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='PUT')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)

#   2
time.sleep(1)
data_json = {
    "temperatura": 8.0
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='PUT')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)

#   3
time.sleep(1)
data_json = {
    "temperatura": 18.0
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='PUT')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)

#   4
time.sleep(1)
data_json = {
    "temperatura": 8.0
}
data = json.dumps(data_json).encode("utf-8")
try:
    req = request.Request(url, data, headers, method='PUT')
    with request.urlopen(req) as f:
        pass
    print(f.status)
except Exception as e:
    print(e)
