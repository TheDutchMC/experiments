using UnityEngine;
public class CameraFollowPlayer : MonoBehaviour {
    public Transform playerTransform;
    public Vector3 cameraOffset;
    
    void Update() {
        transform.position = playerTransform.position + cameraOffset;
    }
}
