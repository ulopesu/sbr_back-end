from urllib import request, parse

import json

url = "https://127.0.0.1:8000/camara/"

data_json = {
    'nome': 'Camara 1 - UFES',
    'loc': {'latitude': -20.273610030740326, 'longitude': -40.30586659439299},
    'temperatura': 16.0,
    'umidade': 80.0
}

data = json.dumps(data_json)

data = str(data)
data = data.encode("utf-8")

req = request.Request(url, data=data, method="POST")

print (req.read())

#data = {'test1': 10, 'test2': 20}
#data = parse.urlencode(data).encode()