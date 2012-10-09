#!/bin/python

fout = file("walls2.txt","w")
for line in file("walls.txt","r"):
  fout.write(line.rstrip()+line)
for line in file("walls.txt","r"):
  fout.write(line.rstrip()+line)
  
fout = file("ground2.txt","w")
for line in file("ground.txt","r"):
  fout.write(line.rstrip()+line)
for line in file("ground.txt","r"):
  fout.write(line.rstrip()+line)