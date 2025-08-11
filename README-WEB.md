# Vikings Rise Simulator - Web Edition

This is a web-based version of the Vikings Rise Simulator, replacing the Java Swing GUI with a modern React frontend that can be deployed to the web.

## Architecture

- **Frontend**: React with TypeScript and styled-components
- **Backend**: Spring Boot REST API
- **Core Logic**: Original Java simulation engine (reused from existing codebase)

## Project Structure

```
Vikings Rise Simulator/
├── frontend/           # React web application
│   ├── src/
│   │   ├── components/ # React components
│   │   ├── types.ts    # TypeScript interfaces
│   │   ├── api.ts      # API client
│   │   └── styled.ts   # Styled components
│   └── package.json
├── backend/            # Spring Boot API
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   ├── com/vikingsrise/simulator/ # New API layer
│   │       │   └── test/                      # Original simulation logic
│   │       └── resources/
│   └── pom.xml
└── demo/              # Original Java Swing application
```

## Features

The web version maintains all core functionality from the original Java Swing application:

### Simulation Modes
- **Trades**: Run simulation with specified number of rounds
- **Plot Fights**: Generate histogram of fight results
- **Group Round Sim**: Run multiple rounds with varying fight lengths

### March Management
- Add friendly marches and enemy dummies
- Configure commanders, stats, and skills
- Clone existing marches
- Support for rally vs field troops

### Results Display
- Real-time simulation results
- Detailed combat statistics
- Trade ratios and enemy losses

## Setup Instructions

### Prerequisites
- Node.js 16+ (for frontend)
- Java 17+ (for backend)
- Maven 3.6+ (for backend)

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Install dependencies and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

   The frontend will start on `http://localhost:3000`

## API Endpoints

- `POST /api/simulate` - Run simulation with configuration and marches
- `GET /api/commanders` - Get list of available commanders
- `GET /api/skills` - Get list of available skills

## Development

### Adding New Features

1. **Frontend**: Add new components in `frontend/src/components/`
2. **Backend**: Add new endpoints in `backend/src/main/java/com/vikingsrise/simulator/controller/`
3. **Core Logic**: Modify existing classes in `backend/src/main/java/test/`

### Configuration

- Frontend proxy is configured to forward API calls to `http://localhost:8080`
- CORS is enabled on the backend for `http://localhost:3000`

## Deployment

### Frontend
The React app can be built for production and deployed to any static hosting service:

```bash
cd frontend
npm run build
```

Deploy the `build/` directory to services like:
- Netlify
- Vercel
- GitHub Pages
- AWS S3

### Backend
The Spring Boot application can be deployed to:
- Heroku
- AWS Elastic Beanstalk
- Google Cloud Run
- Any server with Java 17+

Build a JAR file:
```bash
cd backend
mvn clean package
java -jar target/simulator-backend-0.0.1-SNAPSHOT.jar
```

## Migration from Java Swing

This web version provides the same functionality as the original Java Swing application:

### Original Features ✅
- ✅ Three simulation modes (Trades, Plot Fights, Group Round Sim)
- ✅ March configuration with commanders and skills
- ✅ Enemy dummy creation
- ✅ March cloning
- ✅ Real-time results display
- ✅ Support for all original simulation parameters

### Improvements 🚀
- 🌐 Web-based - no Java installation required
- 📱 Responsive design for mobile devices
- 🚀 Modern React UI with better UX
- 🔄 RESTful API for potential mobile apps
- ☁️ Cloud deployment ready
- 🎨 Improved visual design

### Missing Features 🔄
- 📊 Plotly histogram integration (can be added)
- 💾 Save/load march configurations (can be added)
- 📈 Advanced charting (can be added)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test both frontend and backend
5. Submit a pull request

## License

This project maintains the same license as the original Vikings Rise Simulator.
