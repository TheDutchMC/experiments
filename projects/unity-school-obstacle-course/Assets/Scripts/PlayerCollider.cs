using UnityEngine;
public class PlayerCollider : MonoBehaviour {

    public ScoreTracker scoreTracker;

    void Start() {
    }

    void OnCollisionEnter(Collision collision) {

        if (collision.collider.tag == "Obstacle") {
            scoreTracker.score--;
        }
    }

    private void OnTriggerEnter(Collider collision) {
        if (collision.gameObject.tag == "Point") {
            scoreTracker.score++;
        }
    }
}
