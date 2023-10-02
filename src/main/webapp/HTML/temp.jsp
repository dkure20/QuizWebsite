<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Unlimited Inputs with Radio Buttons</title>
</head>
<body>

<h2>Select an Option:</h2>
<form action="../temp" method="post">
  <input type="text" id="newOption" placeholder="Enter an option">
  <button type="button" id="addOption">Add Option</button>
  <br><br>

  <div id="optionsContainer">
    <!-- Existing options will be displayed here -->
  </div>

  <!-- Hidden input fields for options array and chosen option -->
  <input type="hidden" id="optionsArray" name="optionsArray" value="">
  <input type="hidden" id="chosenOption" name="chosenOption" value="">

  <br>
  <input type="submit" value="Submit">
</form>

<script>
  document.addEventListener("DOMContentLoaded", function() {
  var optionsArray = []; // Array to store options
  var chosenOption = ""; // Variable to store the chosen option

  document.getElementById("addOption").addEventListener("click", function() {
    var newOption = document.getElementById("newOption").value;

    if (newOption.trim() !== "") {
      optionsArray.push(newOption); // Add the option to the array
      if(optionsArray.length === 1){
        document.getElementById("chosenOption").value = newOption;
      }
      var input = document.createElement("input");
      input.type = "radio";
      input.name = "option"; // Keep the name "option" for radio buttons
      input.value = newOption;
      input.checked = true; // Make the newly added option checked

      var textNode = document.createTextNode(newOption);
      var br = document.createElement("br");

      var optionsContainer = document.getElementById("optionsContainer");
      optionsContainer.appendChild(input);
      optionsContainer.appendChild(textNode);
      optionsContainer.appendChild(br);

      // Clear the input field
      document.getElementById("newOption").value = "";

      // Update the hidden input field for options array
      document.getElementById("optionsArray").value = optionsArray.join(",");

      // Update chosen option event listeners
      updateChosenOptionListeners();
    }
  });

  // Function to update chosen option event listeners
  function updateChosenOptionListeners() {
    var radios = document.getElementsByName("option");
    for (var i = 0; i < radios.length; i++) {
      radios[i].addEventListener("click", function() {
        chosenOption = this.value;
        document.getElementById("chosenOption").value = chosenOption;
      });
    }
  }

  // Initial call to update event listeners
  updateChosenOptionListeners();
  });
</script>


</body>
</html>
