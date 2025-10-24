# immfly-store

# Mobile app the allow user the order product

# Requirements

    # Android Studio vertion [Android Studio Narwhal 4 Feature Drop | 2025.1.4]
    # Emulator: from any device running api level above [API level 26]
    # AGP verion [8.13.0]
    # Kotlin veriton: [2.2.20]

# Api server requirement

    # node verion [22.18.0] it probably will work with another version, but keep in mind this version

# firt must run the apir server


## api-Server
    # clone the project: [https://github.com/EmilioBoti/api-server]
 
# api-server setup- in your terminal inside the project file run:
    
    # npm install

# run the server
    # npm run start:api
    # node server.js
    # npx nodemon server.js

# server running in [http://localhost:3000/products]

# App

    # clone the project: [https://github.com/EmilioBoti/immfly-store]

    # open Android Studio
    # Async Gdle and ensure it load with no errors
    # select an emulator and run the app

    # Note: as the server is local it don't work with real devices

# Architecture decisions

    # MVVM: (Model-View-ViewModel)
    # Dependency injection: Dagger-hilt
    # Networking: retrofit + OkHttp
    # Data Storage: Rooom and SharePreferences for local caching data

# Trade-off
    # Using MVVM as mordern Architecture for Android is perfect but there're some other like MVI that can be considered too
    # Room ensure local persistence data but it adds more complexity


# Assumptions and Known limitations
    # Network connectivity is assumed for API request, offline mode is limited to cached data
    # App doest no support landscape correctly