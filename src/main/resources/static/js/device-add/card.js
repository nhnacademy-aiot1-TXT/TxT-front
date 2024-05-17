function createConditionGroup() {
    const container = document.getElementById('conditionsContainer');
    const index = container.childElementCount;

    const group = document.createElement('div');
    group.className = "card mx-auto mb-4";
    group.style.maxWidth = "50%";

    group.innerHTML = `
          <div class="card-header">
              Condition ${index + 1}
              <button type="button" class="btn btn-sm btn-danger float-end ml-1" onclick="(this.parentElement.parentElement).remove()">Delete</button>
              <button
                class="btn btn-sm btn-primary float-end"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#collapseBody${index}"
                aria-expanded="true"
                aria-controls="collapseBody${index}"
              >Toggle</button>
          </div>
          <div id="collapseBody${index}" class="card-body collapse show">
              <div class="form-group">
                  <label for="mqttUrl${index}">MQTT URL</label>
                  <input type="text" id="mqttUrl${index}" name="mqttUrl${index}" class="form-control" >
              </div>

              <div class="form-group">
                  <label for="topic${index}">Topic</label>
                  <input type="text" id="topic${index}" name="topic${index}" class="form-control">
              </div>

              <div class="form-group">
                  <label for="comparisonOperator${index}">Comparison Operator</label>
                <select id="comparisonOperator${index}" name="comparisonOperator${index}" class="form-control">
                    <option value="GREATER_THAN">&gt;</option>
                    <option value="LESS_THAN">&lt;</option>
                    <option value="EQUAL_TO">==</option>
                    <option value="GREATER_THAN_OR_EQUAL_TO">&gt;=</option>
                    <option value="LESS_THAN_OR_EQUAL_TO">&lt;=</option>
                </select>
              </div>

              <div class="form-group">
                  <label for="standardValue${index}">Standard Value</label>
                  <input type="number" step="0.01" id="standardValue${index}" name="standardValue${index}" class="form-control">
              </div>
          </div>`;

    container.appendChild(group);
}

function createAiModeGroup() {
    const container = document.getElementById('aiModesContainer');
    const index = container.childElementCount;

    const group = document.createElement('div');
    group.className = "card mx-auto mb-4";
    group.style.maxWidth = "70%";

    group.innerHTML = `
          <div class="card-header">
              Ai Mode ${index + 1}
              <button type="button" class="btn btn-sm btn-danger float-end ml-1" onclick="(this.parentElement.parentElement).remove()">Delete</button>
              <button
                class="btn btn-sm btn-primary float-end"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#collapseBody${index}"
                aria-expanded="true"
                aria-controls="collapseBody${index}"
              >Toggle</button>
          </div>
          <div id="collapseBody${index}" class="card-body collapse show">
              <div class="form-group">
                  <label for="mqttUrl${index}">MQTT URL</label>
                  <input type="text" id="mqttUrl${index}" name="mqttUrl${index}" class="form-control" >
              </div>

              <div class="form-group">
                  <label for="topic${index}">Topic</label>
                  <input type="text" id="topic${index}" name="topic${index}" class="form-control">
              </div>`;

    container.appendChild(group);
}



