using UnityEngine;
public class PlayerMovement : MonoBehaviour {

    public Rigidbody rb;
    public Transform player;
    public float speedMultiplier = 1000f;
    public float sidewaysForceMultiplier = 25f;

    void FixedUpdate() {
        rb.AddForce(0, 0, -speedMultiplier * Time.deltaTime);
    }

    void Update() {
            
        if(Input.GetKey(KeyCode.D)) {
            rb.AddForce(-sidewaysForceMultiplier * Time.deltaTime, 0, 0, ForceMode.VelocityChange);
        }

        if (Input.GetKey(KeyCode.A)) {
            rb.AddForce(sidewaysForceMultiplier * Time.deltaTime, 0, 0, ForceMode.VelocityChange);
        }

        if(player.position.y < -1f) {
            FindObjectOfType<GameManager>().EndGame(true);
        }
    }
}
