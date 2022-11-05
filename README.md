# file-system

- Simple file system written in kotlin that works only in ram
- It can be used as a tui or can easily integrate into another project


# Contents

## There are 4 different commands being:
1. create
2. delete
3. move
4. write


## There are 4 file types being:
1. Drives
2. Folders
3. Zip files
4. Text Files


# Use


Commands are used in the format 
```
create {Type} {Name} {Path}

delete {Path}

move {Path} {Destination}

write {Path} {new content}
```

## Sample code cases


```
create drive c \c

create folder f \c\f

create zip z \c\z

create file f \c\z\f

delete \c\f

move \c\z\f \c\f

write \c\f
```

# Plans

There are currently no plans to do anything else with this project, it was mainly done as a fun coding exercise, but feel free to use it as if you want


