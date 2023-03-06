rtts = 0.0

a = 0.125
b = .25

rtts = 35
rttm = 15

def findRTTS(rtts, rttm, a): 
    return ((1-a)*rtts) + (a*rttm)

result1 = findRTTS(35, 15)

print(result1)
 
#result2 = (1-a)*result1 + a

#RTTD = (1 - β) x RTTD + β x |RTM - RTS|

rttd = 10
b = 0.25
rtm = 48
rts = 40

def findRTTD(rttd, rtm, rts, b):
    return (1-b)*rttd + b * abs(rtm-rts)


result2 = findRTTD(rttd, rtm, rts, b)
#print(result2)

#RTTO = RTTS + 4 x RTTD
def findRTTO(rts, rttd):
    return rts + 4*rttd

result3 = findRTTO(rts, result2)
#print(result3)