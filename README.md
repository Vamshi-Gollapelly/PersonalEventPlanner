# Personal Event Planner App

## Overview
This project is an Android application that helps users manage their personal events like meetings, trips, and daily tasks. The app allows users to add, view, update, and delete events easily. All the data is stored on the device using Room Database, so it remains saved even after closing the app.

## Features

### Add Event
Users can create a new event by entering:
- Title
- Category (Work, Social, Travel, etc.)
- Location
- Date and Time

### View Events
All saved events are shown on the main screen.  
Events are automatically sorted by date so that the nearest events appear first.

### Update Event
Users can edit any existing event and update details like time, location, or category.

### Delete Event
Users can remove events they no longer need.  
A confirmation message is shown after deletion.

## Data Storage
This app uses **Room Database** to store event data locally.  
The data is not lost when the app is closed or the device is restarted.

## Navigation
The app uses **Bottom Navigation** with two main screens:
- Event List
- Add Event

Fragments are used instead of multiple activities to keep the app smooth and simple.

## Validation and Error Handling
- Title cannot be empty
- Date and time must be selected
- Past dates are not allowed
- Toast messages are used to show success and error messages

## Technologies Used
- Java
- Android Studio
- Room Database
- RecyclerView
- ViewModel and LiveData
- Navigation Component

## How to Run the App
1. Clone this repository
2. Open the project in Android Studio
3. Let Gradle sync
4. Run the app on an emulator or Android device

## Author
Vamshi Gollapelly
