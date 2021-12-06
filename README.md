# Contacts App

Maintainer: Juan Wilfredo Gales Ibañez

<p float="left">
  <img src="/screenshots/contacts-list.jpg" height=500>
  <img src="/screenshots/contacts-details.jpg" height=500>
</p>


This simple app enables users the following:

#### User List Page
- Show a list of users from the api https://jsonplaceholder.typicode.com/users
- Select a user to see their details

#### User Details Page
- Show the user's details: name, username, email. address, phone, website, and company info
- User can click user info to copy it to clipboard
- Quick buttons to call & email the user

## Implementation

- Written in Kotlin
- One activity with two fragments: user list, and user detials fragment
- Fragment navigation via Navigation Controller
- Implemented MVVM architecture via View Binding, ViewModels & LiveData
- Dependency injection via Hilt
- Reactive programming via RxJava
- Material design principles
- Networking layer via Retrofit & OkHttp

## Building locally

Clone the repository via
```
$ git clone https://github.com/jwgibanez/silver-train-cloud
```

Build the release apk
```
$ ./gradlew assembleRelease
```

## Testing

The architecture is conciously designed so that all layers are testable as much as possible.

Automated test have been implemented covering all layers in the MVVM architecture:
- View: UITest (ItemDetailHostActivity)
- ViewModel: ContactsViewModelTest (ContactsViewModel)
- Model: AppDatabaseTest (AppDatabase), UserServiceTest (UserService) etc.

To run all test, attach device to adb and run
```
$ ./gradlew connectedAndroidTest
```

## CI/CD

On every commit to `main`, automated build is done by Github Actions to check the app is building successfully.
