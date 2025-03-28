<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Game Page</title>
		<!-- Load jQuery and the jQuery Terminal plugin -->
  <script src="https://cdn.jsdelivr.net/npm/jquery"></script>
  <script src="https://cdn.jsdelivr.net/npm/jquery.terminal/js/jquery.terminal.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery.terminal/css/jquery.terminal.min.css"/>
  <style type="text/css">
      /* Full-screen terminal container */
      #terminal {
          position: fixed;
          top: 0;
          left: 0;
          width: 100vw;
          height: 100vh;
          background-color: #222;
          color: #fff;
          padding: 10px;
          box-sizing: border-box;
          z-index: 1;
      }
      /* Directional controls container, positioned in the middle right */
      .directional-controls {
          position: fixed;
          right: 20px;
          top: 50%;
          transform: translateY(-50%);
          z-index: 2;
          text-align: center;
      }
      /* Each form appears inline with margin for spacing */
      .directional-controls form {
          display: inline-block;
          margin: 5px 0;
      }
      /* Basic button styling */
      button {
          font-size: 1em;
          padding: 10px 20px;
      }
      .response {
          position: fixed;
          right: 620px;
          top: 50%;
          transform: translateY(-50%);
          z-index: 2;
          text-align: center;
          color: #fff;
      }
  </style>
</head>
<body>
  <!-- Full-screen terminal container -->
  <div id="terminal"></div>
 
  <!-- Directional Controls: Each button sends a direction to the servlet -->
  <div class="directional-controls">
      <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="up">
          <button type="submit">Up</button>
      </form>
      <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="north">
          <button type="submit">North</button>
      </form>
      <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="east">
          <button type="submit">East</button>
      </form>
      <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="south">
          <button type="submit">South</button>
      </form>
      <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="west">
          <button type="submit">West</button>
      </form>
      <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="down">
          <button type="submit">Down</button>
      </form>
  </div>
  
  <div class="response">${response}</div>
 
  <script>
      $(document).ready(function(){
          // Initialize the terminal inside the full-screen container.
          // No command output is added.
          $('#terminal').terminal(function(command, term) {
              // This callback is available if you need to process commands.
          }, {
              greetings: 'Welcome to every breath you take' // No greeting text is shown.
          });
      });
  </script>
</body>
</html>