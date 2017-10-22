# 110-scheduler-server
Java / Postgresql  backend for the 110 scheduling system. Currently, it is developed with Heroku deployment in mind, but can be deployed anywhere with a little extension.

# To set up on a new Heroku instance:
-Clone this repository on your local machine

-Follow the instructions here up to and including the 'Deploy the app' step, but *skip* the Prepare the app step. 
https://devcenter.heroku.com/articles/getting-started-with-java#introduction

-Once finished, run this command in the Heroku CLI to create a Postgres database to store our schedules
*heroku addons:create heroku-postgresql:hobby-dev*

-In the CLI, run this command
*Heroku info*
Make note of the value of the 'Web URL:' field. This will be the value you will put in the scheduler client's config file



