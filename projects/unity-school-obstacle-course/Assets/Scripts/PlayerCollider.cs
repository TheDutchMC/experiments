using UnityEngine;
using UnityEngine.SceneManagement;
public class PlayerCollider : MonoBehaviour {

    public ScoreTracker scoreTracker;

    void OnCollisionEnter(Collision collision) {

        if (collision.collider.tag == "Obstacle") {
            FindObjectOfType<GameManager>().EndGame(true);
        }

        if (collision.collider.tag == "Finish") {
            Invoke("LoadEndScene", 1f);
        }
    }

    private void OnTriggerEnter(Collider collision) {
        if (collision.gameObject.tag == "Point") {
            collision.gameObject.SetActive(false);
            scoreTracker.score++;
        }
    }

    private void LoadEndScene() {
        Debug.Log("Loading end menu");
        SceneManager.LoadScene("EndMenu");
    }
}
