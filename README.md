# SADPS  
## Secure Authentication & Data Protection System

> 🚧 **Status: Under Active Development (Learning & Upskilling Project)**  
> This project is being developed to explore and implement real-world backend
> security, scalability, and monitoring concepts.  
> It is **not production-ready** and is intended for **learning purposes only**.

![Build](https://img.shields.io/badge/build-in--progress-yellow)
![Status](https://img.shields.io/badge/status-learning%20project-orange)
![Java](https://img.shields.io/badge/java-21-blue)
![Spring Boot](https://img.shields.io/badge/spring--boot-4.x-brightgreen)
![License](https://img.shields.io/badge/license-MIT-green)

---

## 🔐 About the Project

**SADPS (Secure Authentication & Data Protection System)** is a backend-focused
Spring Boot project created to **practice and understand real-world security,
performance, and observability patterns** commonly used in enterprise systems.

The goal of this project is **upskilling**, not delivering a production system.

---

## 🚀 Current Focus Areas (Work in Progress)

- 🔑 Authentication & authorization concepts
- 🛡️ Secure handling of sensitive data
- 🧩 Modular Spring Boot architecture
- ⚙️ Event-driven and asynchronous processing
- 📊 Monitoring & observability
- 🚀 Performance optimization techniques

> ⚠️ Some modules are experimental and may change frequently.

---

## 🧠 Planned & Learning-Oriented Implementations

### ✅ Messaging & Events
- Apache Kafka for:
  - Authentication events
  - Security logs
  - Asynchronous processing

### ✅ Data Auditing
- Hibernate Envers for:
  - Entity change tracking
  - Audit logs
  - Historical data analysis

### ✅ Caching & Performance
- Redis cache for:
  - Rate limiting
  - Temporary authentication data
  - Performance optimization

### ✅ Monitoring & Observability
- Spring Boot Actuator
- Prometheus for metrics collection
- Grafana dashboards for visualization

### ✅ Security Enhancements
- CAPTCHA validation (anti-bot protection)
- Role-based access control (RBAC)
- Secure REST APIs

### ✅ Admin Utilities
- REST API to download audit/log data
  - Restricted to admin access
  - Intended for analysis and learning

---

## 🛣️ Roadmap (Learning Goals)

- [ ] Kafka producer & consumer integration
- [ ] Redis caching strategies
- [ ] Hibernate Envers auditing
- [ ] Prometheus metrics exposure
- [ ] Grafana dashboards
- [ ] CAPTCHA validation flow
- [ ] Secure admin-only APIs
- [ ] Docker setup

---

## 🧰 Tech Stack

| Technology | Purpose |
|----------|--------|
| Java 21 | Core language |
| Spring Boot | Backend framework |
| Spring Security | Authentication & authorization |
| Apache Kafka | Event streaming |
| Redis | Caching & rate limiting |
| Hibernate Envers | Audit logging |
| Prometheus | Metrics |
| Grafana | Monitoring dashboards |
| Maven | Build & dependency management |

---

## 📁 Project Structure

SADPS/
├── src/ # Application source code
├── target/ # Build output
├── pom.xml # Maven configuration
├── mvnw / mvnw.cmd # Maven wrapper
├── README.md # Documentation
└── LICENSE # License

yaml
Copy code

---

## 🧪 Running the Project (Development)

### Prerequisites
- Java 21+ (Java 21 recommended)
- Maven or Maven Wrapper
- Git

### Clone & Run

```bash
git clone https://github.com/ascentway/SADPS.git
cd SADPS
./mvnw clean install
./mvnw spring-boot:run

🤝 Contribution Status
🚫 External contributions are currently closed

🐳 Running SADPS with Docker

Docker support is provided to help understand containerization concepts
and to simplify running the backend in a consistent environment.

This setup is intended for **local development and learning purposes only**.

📦 Prerequisites
- Docker (20.x or newer)
- Docker Desktop (Windows / macOS) or Docker Engine (Linux)

🔨 Build Docker Image

From the project root directory (where `Dockerfile` and `pom.xml` exist):

```bash
docker build -t sadps-backend .
⏳ The first build may take a few minutes as Maven dependencies are downloaded.

▶️ Run Docker Container

bash
Copy code
docker run -d -p 8080:8080 --name sadps-backend-container sadps-backend
The application will be available at:
http://localhost:8080

📄 View Application Logs

bash
Copy code
docker logs sadps-backend-container
🛑 Stop & Remove Container

bash
Copy code
docker stop sadps-backend-container
docker rm sadps-backend-container

🧠 Docker Notes 
Uses a multi-stage Docker build

Separates build and runtime environments

Keeps the final image lightweight

Designed for backend containerization practice

⚠️ Disclaimer
This Docker setup does not include:

Externalized secrets management

Production-grade security hardening

Orchestration (Kubernetes, ECS, etc.)

📌 Disclaimer

Not intended for direct production use

Security implementations are educational, not compliance-certified

📄 License
Licensed under the MIT License.
See the LICENSE file for details.

⭐ Vision
To evolve SADPS into a well-documented learning reference
for backend developers exploring security, performance,
and observability in Spring Boot applications.
