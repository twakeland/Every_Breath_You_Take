<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Game Page</title>
		<style>
     body {
        <!-- ChatGPT helped to insert image as a background and properly format it -->
        margin: 0;
        /* Set the background image using a URL */
        background-image: url('https://files.oaiusercontent.com/file-3eEq34wsGtZJZvzCjHoTKf?se=2025-03-21T17%3A44%3A32Z&sp=r&sv=2024-08-04&sr=b&rscc=max-age%3D604800%2C%20immutable%2C%20private&rscd=attachment%3B%20filename%3D86c2dd56-a836-4678-85af-9ed69cf6f6eb.webp&sig=4knZnJGspaBocJ//bI1MC8Yy4uclReawp8izIWL0w%2B4%3D');
                 /* Make sure the image covers the whole screen */
        background-size: cover;
        /* Centers the background image */
        background-position: center;
        /* Take up the full height of the viewport */
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        position: relative;
     }
    
     #title-bar {
        text-align: center;
        font-size: 6em;
        margin-top: 20vh;
        color: white;
     }
    
     .button-container {
        position: absolute;
        bottom: 20px;
        left: 50%;
        transform: translateX(-50%);
        text-align: center;
     }
    
     button {
        font-size: 1.5em;
        padding: 15px 30px;
        margin: 0 10px;
     }
  </style>
</head>
<body>
  <div id="title-bar">
      <div class="divider"></div>
      Every Breath You Take
      <div class="divider"></div>
  </div>
 
  <div class="button-container">
      <!-- start the game -->
      <form action="gamePage" method="get" style="display: inline;">
          <input type="hidden" name="action" value="game">
          <button type="submit">Start Game</button>
      </form>
      <!-- view credits -->
      <form action="index" method="get" style="display: inline;">
          <input type="hidden" name="action" value="multiply">
          <button type="submit">Credits</button>
      </form>
  </div>
</body>
</html>