using UnityEngine;
using UnityEngine.UI;

public class ScoreTracker : MonoBehaviour {

    public long score = 10L;
    public Text pointsText;

    void Update() {
        pointsText.text = score.ToString();
    }

}
