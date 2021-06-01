# Movie-Rating-Mobile-App
This is an android mobile application developed using java, where the user is able to save his/her favorite movies, review each movie and to experience many more features. This mobile application use SQLite local database to save movie data and IMDB-API to extract real ratings and details from IMDB website. 

+++++ Features +++++
1.	User can add a movie with its details and it will be saved in a local database.
2.	View all previously added movies, then categorize favorite ones. 
3.	View all favorite movies deselect them.
4.	Can edit movie details if something needs to be changed.
5.	User can search any movie in database by any detail regarding the movie.
6.	User can view real IMDB ratings of a movie and view its cover photo.

+++++ Setup Instructions +++++
1.	Clone the repository using this link: https://github.com/JaninduJay/Movie-Rating-Mobile-App.git 
2.	Open "Android Studios" and open 'Movie-Ratings' folder from the cloned Repo.
3.	Run the project (You can use either your mobile phone or an emulator to run the app)
4.	If the features inside the rating button is not working: https://imdb-api.com/
	Create an account from link above and get the API-KEY and replace the following code lines with the generated key,
	line181 and 206 in Rating.java and line 51 in RatingTwo.java
