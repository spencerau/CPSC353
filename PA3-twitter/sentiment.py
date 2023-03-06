# twitterexaqmple.py
# Demonstrates connecting to the twitter API and accessing the twitter stream

# Author: Spencer Au
# Email: spau@chapman.edu
# Author: Ewan Shen
# Email: ewshen@chapman.edu
# Course: CPSC 353
# Assignment: PA03 Sentiment Analysis
# Version 1.3
# Date: March 1, 2023

# Demonstrates connecting to the twitter API and accessing the twitter stream

# Prompt the user to enter two search terms.

# Search the twitter stream for the first 100 tweets
# that contain the first search term.

# Set the number of tweets to 10 while testing, and set it to 100 when
# your program is working.

# Calculate a sentiment score for the first search term

# Search the twitter stream for the first 100 tweets that contain the
# second search term.

# Calculate a sentiment score for the second search term

# Determine which search term currently has the most positive sentiment
# on twitter.

# Print the sentiment score for each search term and identify the term with
# the most positive sentiment score.

# flake8 sentiment.py
# python3 sentiment.py < sentiment.input


import twitter
import sys
import codecs
import json


# Go to http://developer.twitter.com/apps/new to create an app and get values
# for these credentials, which you'll need to provide in place of these
# empty string values that are defined as placeholders.
# See https://developer.twitter.com/docs/auth/oauth for more information
# on Twitter's OAuth implementation.

sys.stdout = codecs.getwriter("utf-8")(sys.stdout.detach())
print('Example 1')
print('Establish Authentication Credentials')

# Open the JSON file and load its contents
with open('keys.json') as f:
    data = json.load(f)

# Access the values associated with the keys
CONSUMER_KEY = data['key1']
CONSUMER_SECRET = data['key2']
ACCESS_TOKEN = data['key3']
ACCESS_TOKEN_SECRET = data['key4']


auth = twitter.oauth.OAuth(ACCESS_TOKEN, ACCESS_TOKEN_SECRET,
                           CONSUMER_KEY, CONSUMER_SECRET)

twitter_api = twitter.Twitter(auth=auth)

print("Nothing to see by displaying twitter_api")
print(" except that it's now a defined variable")
print()
print(twitter_api)

# XXX: Set this variable to a trending topic,
# or anything else for that matter. The example query below
# was a trending topic when this content was being developed
# and is used throughout the remainder of this chapter.

score1 = 0
score2 = 0

for i in range(2):
    # q = '#MentionSomeoneImportantForYou'
    q = input('Enter in term ' + str(i+1) + ': ')
    if (i == 0):
        term1 = q
    elif (i == 1):
        term2 = q

    count = 10

    # See https://dev.twitter.com/docs/api/1.1/get/search/tweets

    search_results = twitter_api.search.tweets(q=q, count=count)

    statuses = search_results['statuses']

    # Iterate through 5 more batches of results by following the cursor
    for _ in range(5):
        # print("Length of statuses", len(statuses))
        try:
            next_results = search_results['search_metadata']['next_results']
        # except KeyError, e: # No more results when next_results doesn't exist
        except KeyError:
            break

        # Create a dictionary from next_results, which has the following form:
        # ?max_id=313519052523986943&q=NCAA&include_entities=1
        kwargs = dict([kv.split('=') for kv in next_results[1:].split("&")])

        search_results = twitter_api.search.tweets(**kwargs)
        statuses += search_results['statuses']

    status_texts = [status['text']
                    for status in statuses]
    # Compute a collection of all words from all tweets
    words = [w
             for t in status_texts
             for w in t.split()]
    print("------------------------------------------------------------------")
    print('Example 9. Sentiment Analysis on the search term from Example 5')
    sent_file = open('AFINN-111.txt')

    scores = {}  # initialize an empty dictionary
    for line in sent_file:
        term, score = line.split("\t")
        # The file is tab-delimited.
        # "\t" means "tab character"
        scores[term] = int(score)  # Convert the score to an integer.

    score = 0
    for word in words:
        uword = word.encode('utf-8')
        if word in scores.keys():
            score = score + scores[word]
    print(float(score))
    print()

    if (i == 0):
        score1 = score
    elif (i == 1):
        score2 = score

print("Term 1: " + term1 + "; Score: " + str(score1))
print("Term 2: " + term2 + "; Score: " + str(score2))
print()

if (score1 > score2):
    print(term1 + " has a greater sentiment score than " + term2)
elif (score1 < score2):
    print(term2 + " has a greater score than " + term1)
else:
    print(term1 + " and " + term2 + " have equal sentiment scores")

print()
