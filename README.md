# Car info app for Android

### Scope
This app fetches the attributes of a car from the backend API and renders it in an `Activity`. At the moment it always fetches them from a hard-coded JSON-file, but in the future it can also fetch from backend based on the vin-number. It's completely written in Kotlin.

The app is organized into two modules: 

* `app` which is the actual app containing everything related to UI and business logic. 
* `data` which contains construction of all networking classes, data models and Retrofit interfaces for the API calls.

The app contains error handling for difference scenarios when fetching from backend fails and can also save/restore the downloaded data on screen rotation or if app is destroyed in background.

### Further development and improvements
* For an even more flexbile architecture, Dagger could be used to keep the instance of the presenters and manage other dependencies in an organized way.
* Improve the UI with icons for the different attributes, make some attributes sections collapse/expandable if many more attributes is added to this screen.
* Adding unit-tests