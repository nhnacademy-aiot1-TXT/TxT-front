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
                  <label for="url${index}">MQTT URL</label>
                  <input type="text" id="url${index}" name="url${index}" class="form-control" >
              </div>

              <div class="form-group">
                  <label for="topic${index}">Topic</label>
                  <input type="text" id="topic${index}" name="topic${index}" class="form-control">
              </div>

              <div class="form-group">
                <label for="comparisonOperator${index}">On 비교 연산자</label>
                <select id="comparisonOperator${index}" name="comparisonOperator${index}" class="form-control">
                    <option value="GREATER_THAN">&gt;</option>
                    <option value="LESS_THAN">&lt;</option>
                    <option value="EQUAL_TO">==</option>
                    <option value="GREATER_THAN_OR_EQUAL_TO">&gt;=</option>
                    <option value="LESS_THAN_OR_EQUAL_TO">&lt;=</option>
                </select>
                
                <label for="standardValue${index}">On 기준값</label>
                <input type="number" step="0.1" id="onCondition${index}" name="onCondition${index}" class="form-control">
              </div>
              
              <div class="form-group">
                <label for="comparisonOperator${index}">Off 비교 연산자</label>
                <select id="comparisonOperator${index}" name="comparisonOperator${index}" class="form-control">
                    <option value="GREATER_THAN">&gt;</option>
                    <option value="LESS_THAN">&lt;</option>
                    <option value="EQUAL_TO">==</option>
                    <option value="GREATER_THAN_OR_EQUAL_TO">&gt;=</option>
                    <option value="LESS_THAN_OR_EQUAL_TO">&lt;=</option>
                </select>
                
                <label for="standardValue${index}">Off 기준값</label>
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

var currentPage = 0;


function showPage(index) {
    var pages = document.getElementsByClassName("page");
    pages[currentPage].classList.remove("active");
    currentPage = index;
    pages[currentPage].classList.add("active");
    document.getElementById("prevButton").disabled = currentPage === 0;
    document.getElementById("nextButton").disabled = currentPage === pages.length - 1;
}

function prevPage() {
    if (currentPage > 0) showPage(currentPage - 1);
}

function nextPage() {
    var currentFields = document.getElementsByClassName("page")[currentPage].getElementsByTagName("input");
    for (var i = 0; i < currentFields.length; i++) {
        if (currentFields[i].value === "") {
            alert("빈칸을 채워주세요");
            return;
        }
    }
    if (currentPage < document.getElementsByClassName("page").length - 1) {
        showPage(currentPage + 1);
    }
}

window.onload = function () {
    showPage(0);
}
