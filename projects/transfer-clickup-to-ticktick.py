import requests
import json
import pyautogui, time

#Usage:
# run it, then click in the de window where you'd put the description for a task

token = 'YOUR PERSONAL ACCESS TOKEN'
headers = {'Authorization': token}

#Steps to get folder id:
# GET https://api.clickup.com/api/v2/team
# GET https://api.clickup.com/api/v2/team/{TEAM ID}/space?archived=false
# GET https://api.clickup.com/api/v2/space/{SPACE ID}/folder?archived=false

folder_id = 'someFolderId'
getListsUrl = 'https://api.clickup.com/api/v2/folder/{}/list?archived=false'.format(folder_id)
getListsR = requests.get(getListsUrl, headers=headers)

lists_array = getListsR.json()['lists']
list_ids = []

i = 0
for item in lists_array:
    list_ids.insert(i, item['id'])
    i += 1

getTasksUrl = 'https://api.clickup.com/api/v2/list/{}/task?archived=false'

all_tasks = {}

for list_id in list_ids:
    taskUrl = getTasksUrl.format(list_id)
    taskRequest = requests.get(taskUrl, headers=headers)
    task_json = taskRequest.json()['tasks']

    for task in task_json:
        if task['description'] is None:
            description = ''
        else:
            description = task['description']

        all_tasks[task['name']] = description

time.sleep(5)

for task_name, task_description in all_tasks.items():
    pyautogui.hotkey('ctrl', 'n')
    pyautogui.typewrite(task_name)
    pyautogui.press('enter')
    pyautogui.click()
    pyautogui.typewrite(task_description)
