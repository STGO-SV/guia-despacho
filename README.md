# guia-despacho

Sistema simple de gestion de pedidos y generacion de guias de despacho para una evaluacion de Desarrollo Cloud Native. La aplicacion usa Spring Boot, H2, Spring Data JPA, OpenPDF, almacenamiento temporal local que representa un montaje EFS y subida de archivos PDF a AWS S3.

## Configuracion

Las propiedades principales estan en `src/main/resources/application.properties`:

```properties
app.storage.efs-path=${APP_EFS_PATH:./efs/guias}
app.aws.s3.bucket=${AWS_S3_BUCKET:guia-despacho-bucket}
app.aws.region=${AWS_REGION:us-east-1}
```

Las credenciales AWS no se escriben en el codigo. El SDK las obtiene desde variables de entorno, perfil local o rol IAM de la instancia EC2.

## EFS

`app.storage.efs-path` representa la ruta donde se monta EFS. En desarrollo local puede usarse `./efs/guias`. En EC2 se espera una ruta montada como `/mnt/efs/guias`. La aplicacion guarda ahi el PDF generado antes de subirlo a S3.

## S3

El bucket debe existir previamente. La aplicacion no crea buckets. Las guias se suben con esta estructura de key:

```text
yyyy/MM/dd/{transportista}/guia-{id}.pdf
```

## Endpoints

- `POST /api/guias`: crea una guia, genera PDF temporal y registra metadata.
- `POST /api/guias/{id}/subir-s3`: sube el PDF a S3.
- `GET /api/guias/{id}/descargar?transportista=TRANSPORTISTA`: descarga el PDF si el transportista coincide.
- `PUT /api/guias/{id}`: actualiza la guia y regenera el PDF.
- `DELETE /api/guias/{id}`: elimina metadata, archivo temporal y objeto S3 si existe.
- `GET /api/guias?transportista=TRANSPORTISTA&fecha=YYYY-MM-DD`: consulta guias por transportista y fecha.

## Ejemplo local

```bash
./mvnw clean test
./mvnw spring-boot:run
```

Crear guia:

```bash
curl -X POST http://localhost:8080/api/guias \
  -H "Content-Type: application/json" \
  -d '{
    "numeroGuia": "GD-001",
    "transportista": "Transportes Norte",
    "fecha": "2026-06-08",
    "destinatario": "Cliente Demo",
    "direccionDestino": "Av. Siempre Viva 123",
    "descripcionCarga": "Cajas con insumos"
  }'
```

Subir a S3:

```bash
curl -X POST http://localhost:8080/api/guias/1/subir-s3
```

Descargar:

```bash
curl -L "http://localhost:8080/api/guias/1/descargar?transportista=Transportes%20Norte" -o guia-1.pdf
```

## Docker

```bash
docker build -t guia-despacho .
docker run -p 8080:8080 \
  -e AWS_REGION=us-east-1 \
  -e AWS_S3_BUCKET=guia-despacho-bucket \
  -e APP_EFS_PATH=/mnt/efs/guias \
  -v /mnt/efs/guias:/mnt/efs/guias \
  guia-despacho
```

## CI/CD

El workflow `.github/workflows/deploy.yml` se ejecuta en cada push a `main`. Compila con Maven, construye y publica una imagen en Docker Hub, luego se conecta por SSH a una EC2 para hacer pull, detener el contenedor anterior y ejecutar el nuevo contenedor en el puerto `8080`.

Secrets requeridos:

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`
- `EC2_HOST`
- `EC2_USER`
- `EC2_SSH_KEY`
- `AWS_REGION`
- `AWS_S3_BUCKET`
