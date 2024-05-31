# Java Platformer

### 4: Images

- GamePanel 
    - paintComponent
        - drawImage(img.getSubImage(0, 0, 64, 40), 0, 0, 128, 80, null)
    - create importImg()
        - create InputStream is = getClass().getResourceAsStream("url")
        - img = ImageIO.read(is) inside try and catch statement
    - create subImg variable
    - set subImg in paintcomponent to get other sprites
    - in drawImage set x and y to ints of x and y Delta values
    - setRect Pos in mouseInputs