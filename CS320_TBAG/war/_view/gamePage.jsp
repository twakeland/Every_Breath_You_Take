<!DOCTYPE html>
<html>
  <head>
    <title>Game Page</title>
    <!-- Load jQuery and the jQuery Terminal plugin -->
    <script src="https://cdn.jsdelivr.net/npm/jquery"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery.terminal/js/jquery.terminal.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery.terminal/css/jquery.terminal.min.css"/>
    <style>
      /* Blue ombre background: light blue at top to dark blue at bottom */
      body {
        margin: 0;
        padding: 0;
        background: linear-gradient(to bottom, #87CEFA, #00008B);
      }
      /* Terminal container (left third with 20px margin) */
      #terminal-container {
        position: fixed;
        top: 20px;
        left: 20px;
        bottom: 20px;
        width: calc(33.33% - 40px);
        background-color: #222;
        color: #fff;
        padding: 10px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
      }
      /* Scrollable terminal area */
      #terminal {
        flex: 1;
        overflow-y: auto;
      }
      /* Hide built-in terminal input */
      .jquery-terminal-input {
        display: none !important;
        pointer-events: none !important;
      }
      /* Separate input box styling */
      #input-container {
        margin-top: 10px;
        display: flex;
      }
      #command-input {
        flex: 1;
        padding: 5px;
        font-size: 1em;
      }
      #submit-button {
        padding: 5px 10px;
        font-size: 1em;
      }
      /* Directional controls container on the right */
      .directional-controls {
        position: fixed;
        right: 20px;
        top: 50%;
        transform: translateY(-50%);
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 20px;
      }
      /* Up/Down button container: increased to 90x45 */
      .button-triangle-up,
      .button-triangle-down {
        position: relative;
        width: 90px;
        height: 45px;
        background: none;
        border: none;
        padding: 0;
        cursor: pointer;
      }
      /* Pseudo-element draws the triangle shape for the Up button */
      .button-triangle-up::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        width: 0;
        height: 0;
        border-left: 45px solid transparent;
        border-right: 45px solid transparent;
        border-bottom: 45px solid #fff;
      }
      /* Centered label for Up */
      .button-triangle-up span {
        position: absolute;
        top: 50%;
        left: 0;
        width: 90px;
        text-align: center;
        transform: translateY(-50%);
        color: #000;
        font-weight: bold;
      }
      /* Pseudo-element draws the triangle shape for the Down button */
      .button-triangle-down::before {
        content: "";
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 0;
        border-left: 45px solid transparent;
        border-right: 45px solid transparent;
        border-top: 45px solid #fff;
      }
      /* Centered label for Down */
      .button-triangle-down span {
        position: absolute;
        bottom: 50%;
        left: 0;
        width: 90px;
        text-align: center;
        transform: translateY(50%);
        color: #000;
        font-weight: bold;
      }
      /* Container for the diamond of cardinal direction buttons */
      .diamond-container {
        position: relative;
        width: 180px;
        height: 180px;
      }
      /* Ensure that the forms inside the diamond container are absolutely positioned */
      .diamond-container form {
        position: absolute;
        margin: 0;
      }
      /* Diamond button style: each button is a 60x60 square rotated 45 degrees */
      .button-diamond {
        width: 60px;
        height: 60px;
        background-color: #fff;
        border: none;
        transform: rotate(45deg);
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
      }
      /* Adjust inner text to be upright */
      .button-diamond span {
        transform: rotate(-45deg);
        font-weight: bold;
        color: #000;
      }
      /* Position the diamond buttons so their centers form a diamond:
         North: center at (90, 30) -> top-left corner at (60, 0)
         East: center at (150, 90) -> top-left corner at (120, 60)
         South: center at (90, 150) -> top-left corner at (60, 120)
         West: center at (30, 90) -> top-left corner at (0, 60) */
      .diamond-container .north {
        top: 0;
        left: 60px;
      }
      .diamond-container .east {
        top: 60px;
        left: 120px;
      }
      .diamond-container .south {
        top: 120px;
        left: 60px;
      }
      .diamond-container .west {
        top: 60px;
        left: 0px;
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
    <!-- Terminal container (left third) -->
    <div id="terminal-container">
      <div id="terminal"></div>
      <div id="input-container">
        <input id="command-input" type="text" placeholder="Enter command here..." />
        <button id="submit-button">Submit</button>
      </div>
    </div>
    
    <!-- Directional Controls container (right side) -->
    <div class="directional-controls">
      <!-- Up triangle button -->
      <div class="page-up">
        <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="up">
          <button type="submit" class="button-triangle-up"><span>Up</span></button>
        </form>
      </div>
      
      <!-- Diamond container for cardinal directions -->
      <div class="diamond-container">
        <!-- North -->
        <form action="gamePage" method="post" class="north">
          <input type="hidden" name="direction" value="north">
          <button type="submit" class="button-diamond"><span>North</span></button>
        </form>
        <!-- East -->
        <form action="gamePage" method="post" class="east">
          <input type="hidden" name="direction" value="east">
          <button type="submit" class="button-diamond"><span>East</span></button>
        </form>
        <!-- South -->
        <form action="gamePage" method="post" class="south">
          <input type="hidden" name="direction" value="south">
          <button type="submit" class="button-diamond"><span>South</span></button>
        </form>
        <!-- West -->
        <form action="gamePage" method="post" class="west">
          <input type="hidden" name="direction" value="west">
          <button type="submit" class="button-diamond"><span>West</span></button>
        </form>
      </div>
      
      <!-- Down triangle button -->
      <div class="page-down">
        <form action="gamePage" method="post">
          <input type="hidden" name="direction" value="down">
          <button type="submit" class="button-triangle-down"><span>Down</span></button>
        </form>
      </div>
    </div>
    
    <div class="response">${response}</div>
    
    <script>
      $(document).ready(function(){
          // Initialize the terminal with an empty prompt and disable auto echo.
          var term = $('#terminal').terminal(function(command, termInstance) {
              if (command !== '') {
                  termInstance.echo('> ' + command);
              }
          }, {
              greetings: 'Welcome to every breath you take',
              prompt: '',
              echoCommand: false
          });
          
          // Remove built-in terminal input.
          $('.jquery-terminal-input').remove();
          
          // Prevent key events in the terminal area.
          $('#terminal').on('keydown', function(e) {
              e.preventDefault();
              return false;
          });
          
          // Redirect focus to separate command input when clicking terminal.
          $('#terminal').on('click', function() {
              $('#command-input').focus();
          });
          
          // Handle submission from the separate input box.
          $('#submit-button').on('click', function(){
              var command = $('#command-input').val();
              if (command !== '') {
                  term.exec(command);
                  $('#command-input').val('');
              }
          });
          
          // Allow Enter key to trigger submission.
          $('#command-input').on('keypress', function(e){
              if (e.which === 13) {
                  e.preventDefault();
                  $('#submit-button').click();
              }
          });
      });
    </script>
  </body>
</html>
