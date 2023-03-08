import requests
import json

with open('keys.json') as f:
    data = json.load(f)

# Access the values associated with the keys
CLIENT_ID = data['key1']
CLIENT_SECRET = data['key2']

AUTH_URL = 'https://accounts.spotify.com/api/token'

# POST
auth_response = requests.post(AUTH_URL, {
    'grant_type': 'client_credentials',
    'client_id': CLIENT_ID,
    'client_secret': CLIENT_SECRET,
})

# convert the response to JSON
auth_response_data = auth_response.json()

# save the access token
access_token = auth_response_data['access_token']

headers = {
    'Authorization': 'Bearer {token}'.format(token=access_token)
}

# base URL of all Spotify API endpoints
BASE_URL = 'https://api.spotify.com/v1/'

input('Get info about a track')
# Track ID from the URI
# Oblivion
track_id = '3LGsgpx4TfxhXbr07OFKqs'
# track_id = input('Enter a track id: ')
# actual GET request with proper header
r = requests.get(BASE_URL + 'audio-features/' + track_id, headers=headers)

r = r.json()

print(json.dumps(r, indent=1))
print()
print()

input('Get a list of albums by an artist')
# Grimes
artist_id = '053q0ukIDRgzwTr4vNSwab'
# artist_id = input('Enter an Artist ID: ')

# pull all artists albums
r = requests.get(BASE_URL + 'artists/' + artist_id + '/albums',
                 headers=headers,
                 params={'include_groups': 'album', 'limit': 50})
d = r.json()
# print(json.dumps(d, indent=1))

for album in d['items']:
    print(album['name'], ' --- ', album['release_date'])
