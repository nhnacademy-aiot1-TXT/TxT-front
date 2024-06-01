document.addEventListener('DOMContentLoaded', function () {
    // Get the onValue and offValue fieldsets
    let onValueFieldset = document.getElementById('onValue');
    let offValueFieldset = document.getElementById('offValue');

    // Add event listeners for the onValue buttons
    onValueFieldset.querySelector('.add').addEventListener('click', function () {
        let onValueInput = onValueFieldset.querySelector('input[name="onValue"]');
        let offValueInput = offValueFieldset.querySelector('input[name="offValue"]');
        let onValue = parseInt(onValueInput.value);
        let offValue = parseInt(offValueInput.value);

        // Increment onValue
        onValue++;

        // Ensure onValue is not less than offValue
        if (onValue <= offValue) {
            onValue = offValue + 1;
        }

        // Update the input value
        onValueInput.value = onValue;
    });

    onValueFieldset.querySelector('.sub').addEventListener('click', function () {
        let onValueInput = onValueFieldset.querySelector('input[name="onValue"]');
        let offValueInput = offValueFieldset.querySelector('input[name="offValue"]');
        let onValue = parseInt(onValueInput.value);
        let offValue = parseInt(offValueInput.value);

        // Decrement onValue
        onValue--;

        // Ensure onValue doesn't go below 1
        if (onValue < 1) {
            onValue = 1;
        }

        // Ensure onValue is not less than offValue
        if (onValue <= offValue) {
            onValue = offValue + 1;
        }

        // Update the input value
        onValueInput.value = onValue;
    });

    // Add event listeners for the offValue buttons
    offValueFieldset.querySelector('.add').addEventListener('click', function () {
        let offValueInput = offValueFieldset.querySelector('input[name="offValue"]');
        let onValueInput = onValueFieldset.querySelector('input[name="onValue"]');
        let offValue = parseInt(offValueInput.value);
        let onValue = parseInt(onValueInput.value);

        // Increment offValue
        offValue++;

        // Ensure offValue is not greater than onValue
        if (offValue >= onValue) {
            offValue = onValue - 1;
        }

        // Update the input value
        offValueInput.value = offValue;
    });

    offValueFieldset.querySelector('.sub').addEventListener('click', function () {
        let offValueInput = offValueFieldset.querySelector('input[name="offValue"]');
        let onValueInput = onValueFieldset.querySelector('input[name="onValue"]');
        let offValue = parseInt(offValueInput.value);
        let onValue = parseInt(onValueInput.value);

        // Decrement offValue
        offValue--;

        // Ensure offValue doesn't go below 1
        if (offValue < 1) {
            offValue = 1;
        }

        // Ensure offValue is not greater than onValue
        if (offValue >= onValue) {
            offValue = onValue - 1;
        }

        // Update the input value
        offValueInput.value = offValue;
    });
});
