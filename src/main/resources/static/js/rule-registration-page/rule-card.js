function createConditionGroup() {
    const container = document.getElementById('conditionsContainer');
    const index = container.childElementCount;

    const group = document.createElement('div');
    group.className = "card mx-auto mb-4";
    group.style.maxWidth = "50%";
    group.innerHTML = `
          <div class="card-header">
              Condition ${index + 1}
              <button type="button" class="btn btn-sm btn-danger float-end" onclick="(this.parentElement.parentElement).remove()">삭제</button>
              <button
                class="btn btn-sm btn-primary float-end me-2"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#collapseBody${index}"
                aria-expanded="true"
                aria-controls="collapseBody${index}"
              >접기</button>
          </div>
          
          <div id="collapseBody${index}" class="card-body collapse show">
              <div class="form-group">
                  <label for="customMode_mqttUrl[${index}]">MQTT URL</label>
                  <input type="text" id="customMode_mqttUrl[${index}]" name="customMode_mqttUrl[${index}]" class="form-control" value="134.">
              </div>

              <div class="form-group">
                  <label for="customMode_topic[${index}]">Topic</label>
                  <input type="text" id="customMode_topic[${index}]" name="customMode_topic[${index}]" class="form-control" value="cus/+/">
              </div>

              <div class="form-group">
                <label for="on_comparisonOperator[${index}]">On 비교 연산자</label>
                <select id="on_comparisonOperator[${index}]" name="on_comparisonOperator[${index}]" class="form-control">
                    <option value="GREATER_THAN">&gt;</option>
                    <option value="LESS_THAN">&lt;</option>
                    <option value="EQUAL_TO">==</option>
                    <option value="GREATER_THAN_OR_EQUAL_TO">&gt;=</option>
                    <option value="LESS_THAN_OR_EQUAL_TO">&lt;=</option>
                </select>
                
                <label for="on_standardValue[${index}]">On 기준값</label>
                <input type="number" step="0.1" id="on_standardValue[${index}]" name="on_standardValue[${index}]" class="form-control" value="30">
              </div>
              
              <div class="form-group">
                <label for="off_comparisonOperator[${index}]">Off 비교 연산자</label>
                <select id="off_comparisonOperator[${index}]" name="off_comparisonOperator[${index}]" class="form-control">
                    <option value="GREATER_THAN">&gt;</option>
                    <option value="LESS_THAN">&lt;</option>
                    <option value="EQUAL_TO">==</option>
                    <option value="GREATER_THAN_OR_EQUAL_TO">&gt;=</option>
                    <option value="LESS_THAN_OR_EQUAL_TO">&lt;=</option>
                </select>
                
                <label for="off_standardValue[${index}]}">Off 기준값</label>
                <input type="number" step="0.01" id="off_standardValue[${index}]" name="off_standardValue[${index}]" class="form-control" value="20">
              </div>
          </div>`;

    container.appendChild(group);
}

function createAiModeGroup() {
    const container = document.getElementById('aiModesContainer');
    const index = container.childElementCount;

    const group = document.createElement('div');
    group.className = "card mx-auto mb-4";
    group.style.maxWidth = "50%";
    group.innerHTML = `
          <div class="card-header">
              Ai Mode ${index + 1}
              <button type="button" class="btn btn-sm btn-danger float-end" onclick="(this.parentElement.parentElement).remove()">삭제</button>
              <button
                class="btn btn-sm btn-primary float-end me-2"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#collapseBody${index}"
                aria-expanded="true"
                aria-controls="collapseBody${index}"
              >접기</button>
          </div>
          <div id="collapseBody${index}" class="card-body collapse show">
              <div class="form-group">
                  <label for="aiMode_mqttUrl[${index}]">MQTT URL</label>
                  <input type="text" id="aiMode_mqttUrl[${index}]" name="aiMode_mqttUrl[${index}]" class="form-control">
              </div>

              <div class="form-group">
                  <label for="aiMode_topic[${index}]">Topic</label>
                  <input type="text" id="aiMode_topic[${index}]" name="aiMode_topic[${index}]" class="form-control">
              </div>`;

    container.appendChild(group);
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
    const form = document.getElementById('ruleAddForm');
    if (!(form instanceof HTMLFormElement)) {
        console.error('ruleAddForm is not an HTMLFormElement');
        return;
    }

    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    // AI 모드 데이터 추가
    const aiModesContainer = document.getElementById('aiModesContainer');
    let aiModeHasValue = false;

    const aiModeData = {
        mqttInInfos: [],
        hour: document.getElementById('aiMode_hours').value,
        minutes: document.getElementById('aiMode_minutes').value
    };

    for (let i = 0; i < aiModesContainer.childElementCount; i++) {
        const mqttUrl = document.getElementById(`aiMode_mqttUrl[${i}]`).value;
        const topic = document.getElementById(`aiMode_topic[${i}]`).value;

        if (mqttUrl !== "" || topic !== "") {
            aiModeHasValue = true;
        }
        aiModeData.mqttInInfos.push({mqttUrl, topic});
    }

    if (aiModeHasValue || aiModeData.hour !== "" || aiModeData.minutes !== "") {
        jsonData.aiMode = aiModeData;
    }

    // 커스텀 모드 데이터 추가
    const conditionsContainer = document.getElementById('conditionsContainer');
    let customModeHasValue = true;
    const customModeData = {
        mqttConditionMap: {},
        occupancyCheckRequired: document.getElementById('occupancyCheckRequired').value,
        hour: document.getElementById('customMode_hours').value,
        minutes: document.getElementById('customMode_minutes').value
    };

    if (customModeData.hour === "" || customModeData.minutes === "") {
        customModeHasValue = false;
    }

    for (let i = 0; i < conditionsContainer.childElementCount; i++) {
        const mqttUrl = document.getElementById(`customMode_mqttUrl[${i}]`).value;
        const topic = document.getElementById(`customMode_topic[${i}]`).value;
        const onComparisonOperator = document.getElementById(`on_comparisonOperator[${i}]`).value;
        const onStandardValue = document.getElementById(`on_standardValue[${i}]`).value;
        const offComparisonOperator = document.getElementById(`off_comparisonOperator[${i}]`).value;
        const offStandardValue = document.getElementById(`off_standardValue[${i}]`).value;

        let mqttInfo = {
            "mqttUrl": mqttUrl,
            "topic": topic
        }

        if (mqttUrl === "" || topic === "" || onStandardValue === "" || offStandardValue === "") {
            customModeHasValue = false;
        }

        let mqttInDto = JSON.stringify(mqttInfo)
        customModeData.mqttConditionMap[mqttInDto] = {
            onCondition: {comparisonOperator: onComparisonOperator, standardValue: onStandardValue},
            offCondition: {comparisonOperator: offComparisonOperator, standardValue: offStandardValue}
        };
    }

    if (!customModeHasValue) {
        alert("Custom Mode의 모든 필드를 채워주세요.");
        return;
    }

    jsonData.customMode = customModeData;

    // rule register info request
    const csrfToken = getCookie('XSRF-TOKEN');
    fetch('/admin/rule/send-data', {
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
                window.location.href = '/admin/rule/register';
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

var currentPage = 0;

function showPage(index) {
    var pages = document.getElementsByClassName("page");
    pages[currentPage].classList.remove("active");
    currentPage = index;
    pages[currentPage].classList.add("active");
    document.getElementById("prevButton").disabled = currentPage === 0;

    // 다음 페이지가 없는 경우 다음 버튼을 숨기고 '제출' 버튼으로 변경한다.
    if (currentPage === pages.length - 1) {
        const nextButton = document.getElementById("nextButton");
        nextButton.disabled = true;
    } else {
        const nextButton = document.getElementById("nextButton");
        nextButton.disabled = false;
        nextButton.innerHTML = "다음";
        nextButton.type = "button";
        nextButton.onclick = nextPage;   // '다음' 버튼의 onclick 이벤트 핸들러를 원래대로 복원한다.
    }
}

function prevPage() {
    if (currentPage > 0) showPage(currentPage - 1);
}

function nextPage() {
    if (currentPage < document.getElementsByClassName("page").length - 1) {
        showPage(currentPage + 1);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    showPage(0);
});
