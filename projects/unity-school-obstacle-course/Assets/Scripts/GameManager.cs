using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour {

    bool gameHasEnded = false;

    public void EndGame(bool shouldRestart) {
        if(!gameHasEnded) {
            gameHasEnded = true;

            if (shouldRestart) Invoke("Restart", 1f);

        }
    }

    void Restart() {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

}
