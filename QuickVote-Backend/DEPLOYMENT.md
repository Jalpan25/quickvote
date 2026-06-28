# QuickVote EC2 Docker Deployment

This setup runs the Spring Boot backend and MySQL database on the same EC2 instance.

## 1. Prepare `.env`

Create `.env` from the example:

```bash
cp .env.example .env
```

Set real values:

```env
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

MYSQL_DATABASE=quickvote
MYSQL_ROOT_PASSWORD=use-a-strong-root-password

DB_USERNAME=quickvote_user
DB_PASSWORD=use-a-strong-db-password

JWT_SECRET=use-a-long-random-secret
```

`DB_URL` is overridden by `docker-compose.yml` so the backend connects to the MySQL container at `mysql:3306`.

## 2. Start On EC2

Install Docker and the Compose plugin, then run:

```bash
docker compose up -d --build
```

Check status and logs:

```bash
docker compose ps
docker compose logs -f backend
docker compose logs -f mysql
```

The backend listens on port `8080`. Open port `8080` in the EC2 security group if the API should be public.

MySQL is bound to `127.0.0.1:3306` on the EC2 host, so it is not exposed publicly. The database data is stored in the Docker volume `quickvote-backend_mysql_data`.

## 3. Update Deployment

After pulling new code:

```bash
docker compose up -d --build
```

## 4. Stop Deployment

```bash
docker compose down
```

To remove database data too:

```bash
docker compose down -v
```
