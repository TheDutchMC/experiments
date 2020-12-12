using UnityEngine;
using UnityEngine.SceneManagement;

public class StartGame : MonoBehaviour {

    public void StartLevel() {
        Debug.Log("Start!");
        SceneManager.LoadScene("Game");
    }
}
