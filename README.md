# 🚗 Parking POS - Smart Parking Management System

A modern, microservices-based parking management application built with Spring Boot and React.

## 🌐 Live Demo

**🚀 Production Application:** [https://grand-enchantment-production.up.railway.app/](https://grand-enchantment-production.up.railway.app/)

Experience the full parking management system in action with real-time check-in/check-out functionality.

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Deployment](#-deployment)
- [Contributing](#-contributing)

## ✨ Features

- **Smart Check-in System** - Automated ticket generation with unique identifiers
- **Real-time Check-out** - Instant payment calculation and ticket validation
- **Ticket Management** - Comprehensive ticket lifecycle management
- **Microservices Architecture** - Scalable and maintainable backend services
- **Modern Web Interface** - Responsive React-based frontend
- **Database Migrations** - Automated schema management with Flyway
- **Health Monitoring** - Built-in health checks and monitoring endpoints

## 🏗️ Architecture

The application follows a microservices architecture pattern with three main services:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Check-in      │    │  Check-out      │    │  Ticket         │
│  Service       │    │  Service        │    │  Service        │
│  (Port 8081)   │    │  (Port 8083)    │    │  (Port 8082)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │   PostgreSQL    │
                    │   Database      │
                    └─────────────────┘
```

## 🛠️ Tech Stack

### Backend
- **Java 17** - Modern Java runtime
- **Spring Boot 3.5.4** - Application framework
- **Spring WebFlux** - Reactive web framework
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Primary database
- **Flyway** - Database migration tool
- **Maven** - Build and dependency management
- **Lombok** - Boilerplate code reduction

### Frontend
- **React 19.1.0** - Modern UI framework
- **Vite** - Fast build tool and dev server
- **Axios** - HTTP client for API communication
- **React Router** - Client-side routing
- **ESLint** - Code quality and consistency

### Infrastructure
- **Railway** - Cloud deployment platform
- **Docker** - Containerization support
- **Health Checks** - Application monitoring

## 📁 Project Structure

```
parking-pos-app/
├── backend/
│   ├── checkin-service/          # Check-in microservice
│   ├── checkout-service/         # Check-out microservice
│   └── ticket-service/           # Ticket management service
├── parking-pos/                  # React frontend application
├── script_python/                # Data processing scripts
└── linkedlist/                   # Data structure implementations
```

### Backend Services

#### Check-in Service
- **Port:** 8081
- **Purpose:** Handles vehicle check-in and ticket creation
- **Features:** Ticket generation, validation, status management

#### Check-out Service
- **Port:** 8083
- **Purpose:** Manages vehicle check-out and payment calculation
- **Features:** Duration calculation, fee computation, ticket closure

#### Ticket Service
- **Port:** 8082
- **Purpose:** Core ticket management and persistence
- **Features:** CRUD operations, database management, migrations

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm
- PostgreSQL 12+

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd parking-pos-app/backend
   ```

2. **Configure database**
   ```bash
   # Set environment variables
   export DATABASE_URL=jdbc:postgresql://localhost:5432/parking_pos
   export DATABASE_USERNAME=postgres
   export DATABASE_PASSWORD=postgres
   ```

3. **Start services**
   ```bash
   # Check-in Service
   cd checkin-service
   mvn spring-boot:run
   
   # Check-out Service
   cd ../checkout-service
   mvn spring-boot:run
   
   # Ticket Service
   cd ../ticket-service
   mvn spring-boot:run
   ```

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd parking-pos-app/parking-pos
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

4. **Build for production**
   ```bash
   npm run build
   npm run preview
   ```

## 📚 API Documentation

### Check-in Service Endpoints
- `POST /api/checkin/ticket` - Create new parking ticket
- `GET /api/checkin/health` - Service health check

### Check-out Service Endpoints
- `POST /api/checkout/process` - Process vehicle check-out
- `GET /api/checkout/preview/{ticketId}` - Get check-out preview
- `GET /api/checkout/health` - Service health check

### Ticket Service Endpoints
- `GET /api/tickets` - List all tickets
- `GET /api/tickets/{id}` - Get ticket by ID
- `POST /api/tickets` - Create new ticket
- `PUT /api/tickets/{id}` - Update ticket
- `DELETE /api/tickets/{id}` - Delete ticket
- `GET /actuator/health` - Health monitoring

## 🚀 Deployment

### Railway Deployment

The application is deployed on Railway with automatic deployments from the main branch.

**Production URL:** [https://grand-enchantment-production.up.railway.app/](https://grand-enchantment-production.up.railway.app/)
