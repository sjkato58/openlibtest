# openlibtest
Test project which uses the Open Library API.

Code setup which uses the Open Library API to download data and display using:
* MVVM
* RxJava
* Retrofit
* Hilt

## Open Library API

API being used is the following: https://openlibrary.org/developers/api

The project is to primarily use the My Books API to display a list of data:

https://openlibrary.org/dev/docs/api/mybooks

In order to get the details for the individual works, am also using the following API:

https://openlibrary.org/dev/docs/api/books

## Code Challenge Notes

Following the instructions, the project is using the libraries requested, along with some others.  It is following the Clean Architecture format:

UI layer -> Screens & ViewModels

Domain layer -> UseCase's and Ui relevant Data Classes

Data layer -> Repositories, Data Sources and Api relevant Data Classes.

### Feature Screens

It is setup so that all the Screens are setup using the following pattern:

1 - ***Route -> Used for Navigation Compose purposes to interface between NavHost and Screen component.  Holds the ViewModel, obtaining the UiState and passing that into the Screen.

2 - ***Screen -> Receives the UiState from the Route composable.  This is to make potential Previews (and screen testing) easier and allow for better separation of responsibility.

3 - ***ViewModel -> If required, this is used to control UI interface and domain layer behaviour.

### Navigation

Primarily setup to use Navigation Compose, though it does not take full advantage of this due to the nature of the challenge.  It does use the AppState strategy for Navigation handling.

### Modularisation question

I considered modularising the project, it is the usual strategy I follow with my projects.  However, I am also aware that it can increase the coding time to do so and given the size of the project, it was not worth it.

If i had more time, I would modularise it so that it had 2 modules on top of the 'app' module:

1 - Domain Module - which would contain all the UseCase relevant code.

2 - Data Module - which would contain all the Retrofit, Repository and Data Source relevant code.

## Comments

Overall, a fun challenge, though I think that I would have asked more questions on some specifics of the challenge itself.  I was uncertain of what details could be used for the Book Details Screen, so I may have overdone things.

### Code Reminders

It has been some time since I worked with Rxjava, so it was a bit of a challenge to set the code up.  However, it was a very enjoyable moment of rediscovery to see how the code had changed.

Using Coil was fascinating as previously I would use Glide for displaying Image Urls.  This was before I started using Jetpack Compose and though there is a library for Glide to be used, it is currently in alpha stage and I prefer not to use non stable libraries where possible.  Coil had similarities and integrates smoothly into Compose.

### API Shenanigans

One thing which did cause me some issues was partway through working on the project, the API started changing some of its content.  This is not something I am unfamiliar with (backend updating feeds without frontend receiving notice; communications breakdowns happen) but it did lead to some time required to adjust the code so that it would not break due to the API not being available (useful for error testing) or the values inside the API Json feed changing.

While stressful, it was a good experience to compromise when a feed is being updated but one cannot rely on if the old feed or new feed was going to be in existence.