# JSPetriNet

MRSPN 解析が行えるツールです．

例：以下を example.spn ファイルとして保存

```
place Ph
place Pfh
place Pchm
place Pwhm
place Pbc_d
place Pbw
place Phcm
place Phwm
place Pwr
place Pw
place Pfw
place Pcwm
place Pbc_dd
place Pwcm
place Pcr
place Pc
place Pfc

exp Thr
exp Thf (rate = r1, guard = g2)
exp Tbchf (guard = g1, rate = r1)
exp Tbwhf (rate = r1)
exp Tchm
exp Twhm
exp Twr (guard = g4)
exp Thcm
exp Thwm
exp Twf (guard = g3, rate = r2)
exp Tbcwf (rate = r2)
exp Tcf (rate = r3)
exp Tcwm
exp Tcr (guard = g5)
exp Twcm

imm tcr1
imm twr1
imm twr2
imm tcr2
imm tcr3

iarc Ph to Thf
iarc Ph to Tbchf
iarc Ph to Tbwhf
iarc Pfh to Thr
iarc Pchm to Tchm
iarc Pwhm to Twhm
iarc Pbc_d to tcr1
iarc Pbw to twr1
iarc Phcm to Thcm
iarc Phwm to Thwm
iarc Pwr to twr1
iarc Pwr to twr2
iarc Pw to Twf
iarc Pw to Tbwhf
iarc Pw to Tbcwf
iarc Pfw to twr2
iarc Pcwm to Tcwm
iarc Pbc_dd to tcr2
iarc Pwcm to Twcm
iarc Pcr to tcr1
iarc Pcr to tcr2
iarc Pcr to tcr3
iarc Pfc to tcr3
iarc Pc to Tcf
iarc Pc to Tbcwf
iarc Pc to Tbchf

oarc Thr to Ph
oarc Thf to Pfh
oarc Tbchf to Pchm
oarc Tbwhf to Pwhm
oarc Tchm to Ph
oarc Tchm to Pbc_d
oarc Twhm to Ph
oarc Twhm to Pbw
oarc tcr1 to Phcm
oarc twr1 to Phwm
oarc twr2 to Pw
oarc Twr to Pwr
oarc Thcm to Pc
oarc Thwm to Pw
oarc Twf to Pfw
oarc Tbcwf to Pcwm
oarc Tcwm to Pbc_dd
oarc Tcwm to Pw
oarc tcr2 to Pwcm
oarc Tcr to Pcr
oarc tcr3 to Pc
oarc Tcf to Pfc
oarc Twcm to Pc

r1 := #Ph * lambda_h
r2 := #Pw * lambda_w
r3 := #Pc * lambda_c

g1 := #Pw == 0
g2 := (#Pw == 0) && (#Pc == 0)
g3 := #Pc == 0
g4 := #Pfw + #Pbw > 0
g5 := #Pfc + #Pbc_d + #Pbc_dd > 0

reward1 := ifelse(#Ph >= 1, 1.0, 0.0)
```


