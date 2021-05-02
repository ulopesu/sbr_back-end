#!/usr/bin/env python

from urllib import request, parse

import json

url = "http://localhost:8080/camara"

data_json = {
    "nome": "Camara 1 - UFES",
    "loc": {"latitude": -20.273610030740326, "longitude": -40.30586659439299},
    "temperatura": 16.0
}

data = json.dumps(data_json).encode("utf-8")
#print(data)

headers = {
    "Content-Type": "application/json",
    "Accept": "application/json",
}

try:
    req = request.Request(url, data, headers)
    with request.urlopen(req) as f:
        res = f.read()
    print(res.decode())
except Exception as e:
    a=1

#req = request.Request(url, method="GET")
#with request.urlopen(req) as f:
#    print(f.read().decode("utf-8"))

