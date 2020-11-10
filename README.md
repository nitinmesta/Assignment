# Assignment
assignment

* Implementation Details
1. On initial launch, when there are no saved data an empty view is displayed.
2. Subsequent launch, if there were previous search, the data will be displayed from room db storage
3. On search of city name, if the city is found, it will be add to the list and displayed
4. if the searched city was already part of the previous search, the city will be moved as the first item
5. if there were no matching city, a error is shown with snackback
6. Onclick of city , the details page will open with an animation
7. Only first data shown is the data retrieved from api, since the free version of the end points was used. Next 6 days data is the fake data created by the app
8. There are some sanity checks which is in place to check internet connection , and when the api endpoint encounters some issue

Programming Language : Kotlin
Libararies : Retrofit,Glide(Image Loading),Android Arch component, Room, Material component for recycler view and snack bar, 
