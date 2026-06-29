# QuickVote EC2 Docker Deployment

This setup runs only the Spring Boot backend on EC2. The backend connects to an existing MySQL database on Amazon RDS.

## 1. Prepare `.env`

Create `.env` from the example:

```bash
cp .env.example .env
```

Set real values:

```env
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

DB_URL=jdbc:mysql://your-rds-endpoint.ap-south-1.rds.amazonaws.com:3306/quickvote?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=your-rds-username
DB_PASSWORD=your-rds-password

JWT_SECRET=use-a-long-random-secret
```

Replace `your-rds-endpoint.ap-south-1.rds.amazonaws.com`, `quickvote`, `DB_USERNAME`, and `DB_PASSWORD` with your RDS endpoint, database name, username, and password.

## 2. Start On EC2

Install Docker and the Compose plugin, then run:

```bash
docker compose up -d --build
```

Check status and logs:

```bash
docker compose ps
docker compose logs -f backend
```

The backend listens on port `8080`. Open port `8080` in the EC2 security group if the API should be public.

Allow the EC2 instance to connect to RDS on port `3306`. The usual setup is to add the EC2 security group as an inbound MySQL source in the RDS security group.

## 3. Update Deployment

After pulling new code:

```bash
docker compose up -d --build
```

## 4. Stop Deployment

```bash
docker compose down
```
