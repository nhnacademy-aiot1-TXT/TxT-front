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
                  <input type="text" id="url${index}" name="url${index}" class="form-control" value="134.">
              </div>

              <div class="form-group">
                  <label for="topic${index}">Topic</label>
                  <input type="text" id="topic${index}" name="topic${index}" class="form-control" value="cus/+/">
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
                <input type="number" step="0.1" id="onStandardValue${index}" name="standardValue${index}" class="form-control" value="30">
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
                <input type="number" step="0.01" id="offStandardValue${index}" name="standardValue${index}" class="form-control" value="20">
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
                  <label for="aiMode_mqttUrl[${index}]">MQTT URL</label>
                  <input type="text" id="aiMode_mqttUrl[${index}]" name="aiMode_mqttUrl[${index}]" class="form-control" value="133.">
              </div>

              <div class="form-group">
                  <label for="aiMode_topic[${index}]">Topic</label>
                  <input type="text" id="aiMode_topic[${index}]" name="aiMode_topic[${index}]" class="form-control" value="ai/+/">
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

    // 다음 페이지가 없는 경우 버튼을 '제출' 버튼으로 변경한다.
    if (currentPage === pages.length - 1) {
        const nextButton = document.getElementById("nextButton");
        nextButton.hidden = true;
    } else {
        const nextButton = document.getElementById("nextButton");
        nextButton.innerHTML = "다음";
        nextButton.type = "button";
        nextButton.onclick = nextPage;   // '다음' 버튼의 onclick 이벤트 핸들러를 원래대로 복원한다.
    }
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


function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

function submitForm() {
    const form = document.getElementById('deviceAddForm');
    if (!(form instanceof HTMLFormElement)) {
        console.error('deviceAddForm is not an HTMLFormElement');
        return;
    }

    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    // AI 모드 데이터 추가
    const aiModesContainer = document.getElementById('aiModesContainer');
    if (aiModesContainer.childElementCount > 0) {
        jsonData.aiMode = {
            mqttInDtos: [],
            minutes: document.getElementById('aiMinutes').value
        };
        for (let i = 0; i < aiModesContainer.childElementCount; i++) {
            const mqttUrl = document.getElementById(`aiMode_mqttUrl[${i}]`).value;
            const topic = document.getElementById(`aiMode_topic[${i}]`).value;
            jsonData.aiMode.mqttInDtos.push({mqttUrl, topic});
        }
    }

    // 커스텀 모드 데이터 추가
    const conditionsContainer = document.getElementById('conditionsContainer');
    if (conditionsContainer.childElementCount > 0) {
        jsonData.customMode = {
            mqttConditionMap: {},
            hour: document.getElementById('hours').value,
            minutes: document.getElementById('minutes').value
        };
        for (let i = 0; i < conditionsContainer.childElementCount; i++) {
            const mqttUrl = document.getElementById(`url${i}`).value;
            const topic = document.getElementById(`topic${i}`).value;
            const onComparisonOperator = document.getElementById(`comparisonOperator${i}`).value;
            const onStandardValue = document.getElementById(`onStandardValue${i}`).value;
            const offComparisonOperator = document.getElementById(`comparisonOperator${i}`).value;
            const offStandardValue = document.getElementById(`offStandardValue${i}`).value;

            jsonData.customMode.mqttConditionMap[`mqttIn${i}`] = {
                mqttUrl,
                topic,
                onCondition: {comparisonOperator: onComparisonOperator, standardValue: onStandardValue},
                offCondition: {comparisonOperator: offComparisonOperator, standardValue: offStandardValue}
            };
        }
    }

    const csrfToken = getCookie('XSRF-TOKEN');
    console.log('Submitting JSON Data:', JSON.stringify(jsonData));  // JSON 데이터 구조 확인

    fetch('/admin/device/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(jsonData)
    })
        .then(response => {
            if (response.ok) {
                alert('장치가 성공적으로 등록되었습니다.');
                window.location.href = '/admin/device/add';
            } else {
                return response.json().then(data => {
                    console.error('Error:', data);
                    alert('장치 등록에 실패했습니다.');
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('장치 등록에 실패했습니다.');
        });
}

window.onload = function () {
    showPage(0);
}