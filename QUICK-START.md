# Quick Start Guide

## Vikings Rise Simulator - Web Edition

You've successfully created a modern, web-based version of the Vikings Rise Simulator! Here's how to get started:

## What You Have

✅ **React Frontend** - Modern web interface with responsive design
✅ **Spring Boot Backend** - RESTful API that uses your original simulation logic
✅ **Original Core Logic** - All your combat simulation algorithms preserved
✅ **Web-Ready** - Can be deployed to any hosting service

## Starting the Application

### Option 1: Use the Setup Script (Recommended)
First, navigate to the project directory:
```bash
cd "/Users/td/Desktop/Vikings Rise Simulator"
./setup.sh
```

Then start both servers:
```bash
./start.sh
```

### Option 2: Manual Start

First, navigate to the project directory:
```bash
cd "/Users/td/Desktop/Vikings Rise Simulator"
```

**Terminal 1 - Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm start
```

## Accessing the Application

- **Web Interface**: http://localhost:3000
- **API Documentation**: http://localhost:8080/api

## Features Available

### 🎯 Simulation Modes
- **Trades**: Calculate trade efficiency over multiple rounds
- **Plot Fights**: Generate fight outcome distributions  
- **Group Round Sim**: Analyze performance across different fight lengths

### ⚔️ March Configuration
- Add friendly marches and enemy dummies
- Configure commanders from your skill database
- Set attack, defense, health, and troop counts
- Choose from all available skills (normal, mount slot 1 & 2)
- Rally vs Field troop types

### 📊 Results
- Real-time simulation results
- Trade ratios and combat statistics
- Detailed per-combatant breakdowns

## Migration Benefits

### From Java Swing → React Web

✅ **Accessibility**: No Java installation required
✅ **Mobile Friendly**: Works on phones and tablets
✅ **Modern UI**: Clean, intuitive interface
✅ **Web Deployment**: Can be hosted online
✅ **Cross-Platform**: Works on any device with a browser
✅ **Maintainable**: Modern tech stack for easier updates

## Next Steps

### For Development
1. **Add Features**: Extend the React components in `frontend/src/components/`
2. **API Endpoints**: Add new endpoints in `backend/.../controller/`
3. **Styling**: Customize the look in `frontend/src/styled.ts`

### For Deployment
1. **Frontend**: `npm run build` → Deploy to Netlify/Vercel/etc.
2. **Backend**: `mvn package` → Deploy to Heroku/AWS/etc.

### Potential Enhancements
- 📊 Add interactive charts (Plotly.js integration)
- 💾 Save/load march configurations
- 📱 Mobile app using React Native
- 🔗 Share simulation results via URLs
- 📈 Historical analysis and trends
- 🎨 Custom themes and layouts
- 👥 Multi-user support

## File Structure

```
Vikings Rise Simulator/
├── frontend/              # React web app
│   ├── src/
│   │   ├── components/    # React components
│   │   ├── App.tsx        # Main application
│   │   ├── types.ts       # TypeScript interfaces
│   │   └── styled.ts      # UI styling
│   └── package.json
├── backend/               # Spring Boot API
│   ├── src/main/java/
│   │   ├── com/vikingsrise/simulator/  # New API layer
│   │   └── test/                       # Your original logic
│   └── pom.xml
├── setup.sh              # One-time setup
├── start.sh              # Start both servers
└── README-WEB.md         # Detailed documentation
```

## Troubleshooting

### Backend Won't Start
- Ensure Java 17+ is installed: `java --version`
- Check Maven installation: `mvn --version`
- Review logs in the terminal

### Frontend Won't Start
- Ensure Node.js 16+ is installed: `node --version`
- Try clearing cache: `npm cache clean --force`
- Delete `node_modules` and run `npm install` again

### API Connection Issues
- Backend must be running on port 8080
- Frontend proxy is configured automatically
- Check CORS settings in backend if needed

## Support

If you encounter issues:
1. Check the detailed README-WEB.md
2. Review the console logs in both terminals
3. Ensure all prerequisites are installed
4. Try the manual setup steps

## Success! 🎉

Your Vikings Rise Simulator is now running with real data! 

### ✅ **What's Working**

- ✅ **Backend**: Successfully loaded your original skill database
- ✅ **Frontend**: React app running with real commanders and skills  
- ✅ **API Integration**: Frontend is loading data from your SkillDatabase.json
- ✅ **Real Data**: 
  - **Commanders**: Vali, Gunnar, Heidrun, Sigrid, Alf, Greta, Sephina, Charlton, Hilda
  - **Skills**: All your original skills with proper categories (normal, mountSlot1, mountSlot2)

### 🌐 **Access Your Application**

- **Web Interface**: http://localhost:3000
- **Backend API**: http://localhost:8080

You now have a modern, web-based Vikings Rise Simulator that:
- Uses your original combat simulation logic
- Loads real commanders and skills from your database
- Provides a modern web interface accessible from any browser
- Can be easily shared and deployed online
- Is ready for future enhancements

### 🎯 **Next Steps**

1. **Test the Simulation**: Try running different simulation modes with your real data
2. **Customize**: Modify the UI styling in `frontend/src/styled.ts`
3. **Deploy**: Use the build commands to deploy to web hosting services
4. **Extend**: Add new features like charts, saved configurations, etc.

Start simulating battles with your real Vikings Rise data in your browser!
