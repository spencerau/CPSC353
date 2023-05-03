#!/bin/bash

#!/bin/bash

# Get list of open terminal processes
processes=$(osascript -e 'tell application "System Events" to get the name of every process whose visible is true')

# Loop through processes and close terminals
for process in $processes; do
  if [[ $process == Terminal ]]; then
    osascript -e 'tell application "Terminal" to quit'
  fi
done


javac *.java

# Open terminal window for server
osascript -e 'tell application "Terminal" to do script "cd /Users/spencerau/Documents/GitHub/CPSC353/chatgame && java MtServer"'

# Open terminal window for client 1
osascript -e 'tell application "Terminal" to do script "cd /Users/spencerau/Documents/GitHub/CPSC353/chatgame && java MtClient"'

# Open terminal window for client 2
osascript -e 'tell application "Terminal" to do script "cd /Users/spencerau/Documents/GitHub/CPSC353/chatgame && java MtClient"'

# Open terminal window for client 3
osascript -e 'tell application "Terminal" to do script "cd /Users/spencerau/Documents/GitHub/CPSC353/chatgame && java MtClient"'
