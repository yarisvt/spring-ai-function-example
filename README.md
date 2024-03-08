## Version 0.8.0
With `MessageType.SYSTEM` in `FunctionMessage`
```bash
➜ http :8080/ai/car-model                                                                                
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Fri, 08 Mar 2024 15:30:51 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "carModel": "Kia Sorento PHEV Edition 1.6 T-GDi Plug-in Hybrid AT6 AWD",
    "idCar": "Unknown"
}
```

## Version 0.8.1-SNAPSHOT
With `MessageType.FUNCTION` in `FunctionMessage`
```bash
➜ http :8080/ai/car-model                                                                                
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Fri, 08 Mar 2024 15:30:51 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "carModel": "Kia Sorento 1.6 T-GDi AT6 PHEV 4WD Edition 5d 195kW",
    "idCar": "4"
}
```