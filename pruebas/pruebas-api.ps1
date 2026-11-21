# Pruebas manuales para GA7-220501096-AA5-EV01
# Ejecutar con la API iniciada en http://localhost:8080

$baseUrl = "http://localhost:8080/api/auth"

function Enviar-Post {
    param(
        [string]$Nombre,
        [string]$Ruta,
        [hashtable]$Datos
    )

    Write-Host "`n=== $Nombre ===" -ForegroundColor Cyan

    try {
        $body = $Datos | ConvertTo-Json
        $respuesta = Invoke-RestMethod `
            -Uri "$baseUrl/$Ruta" `
            -Method POST `
            -ContentType "application/json" `
            -Body $body

        $respuesta | ConvertTo-Json
    }
    catch {
        $status = [int]$_.Exception.Response.StatusCode
        Write-Host "HTTP $status" -ForegroundColor Yellow

        if ($_.ErrorDetails.Message) {
            Write-Host $_.ErrorDetails.Message
        }
    }
}

Enviar-Post "Registro correcto" "registro" @{
    usuario = "carlos"
    password = "Carlos123"
}

Enviar-Post "Login correcto" "login" @{
    usuario = "carlos"
    password = "Carlos123"
}

Enviar-Post "Login incorrecto" "login" @{
    usuario = "carlos"
    password = "Incorrecta123"
}

Enviar-Post "Usuario duplicado" "registro" @{
    usuario = "carlos"
    password = "Carlos123"
}

Enviar-Post "Validación de campos" "registro" @{
    usuario = ""
    password = "123"
}
