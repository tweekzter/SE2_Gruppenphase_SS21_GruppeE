package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Dice extends AppCompatActivity {
    Button wuerfel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        ImageView bildergebnis = findViewById(R.id.diceresult);
        ImageView tile1 = (ImageView)findViewById(R.id.tile1);
        ImageView tile2 = (ImageView)findViewById(R.id.tile2);
        ImageView tile3 = (ImageView) findViewById(R.id.tile3);
        AnimationDrawable imagesAnimation;
        AnimationDrawable imagesAnimationtiles;
        AnimationDrawable imagesAnimationtiles2;
        AnimationDrawable imagesAnimationtiles3;
        bildergebnis.setBackgroundResource(R.drawable.dice_slideshow);
        tile1.setBackgroundResource(R.drawable.tiles_slideshow);
        tile2.setBackgroundResource(R.drawable.tiles_slideshow);
        tile3.setBackgroundResource(R.drawable.tiles_slideshow);
        imagesAnimation = (AnimationDrawable) bildergebnis.getBackground();
        imagesAnimationtiles = (AnimationDrawable) tile1.getBackground();
        imagesAnimationtiles2 = (AnimationDrawable) tile2.getBackground();
        imagesAnimationtiles3 = (AnimationDrawable) tile3.getBackground();
        imagesAnimation.start();
       /* imagesAnimationtiles.start();
        imagesAnimationtiles2.start();
        imagesAnimationtiles3.start();*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imagesAnimation.stop();
                /*imagesAnimationtiles.stop();
                imagesAnimationtiles2.stop();
                imagesAnimationtiles3.stop();*/
                test();
            }
        }, 3000);

    }



    private void test() {
        TextView ergebnis = findViewById(R.id.Ergebnis);
        ImageView bildergebnis = findViewById(R.id.diceresult);
        int zahl = (int) (Math.random() * 6 + 1);
        switch (zahl) {
            case 1:
                ergebnis.setText("Löwe");
                Toast.makeText(this, "Löwe", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.lion);
                break;
            case 2:
                ergebnis.setText("Hand");
                Toast.makeText(this, "Hand", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.hand);
                break;
            case 3:
                ergebnis.setText("Antilope");
                Toast.makeText(this, "Antilope", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.antilope);
                break;
            case 4:
                ergebnis.setText("Schlange");
                Toast.makeText(this, "Schlange", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.snake);
                break;
            case 5:
                ergebnis.setText("Elefant");
                Toast.makeText(this, "Elefant", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.elefant);
                break;
            case 6:
                ergebnis.setText("Käfer");
                Toast.makeText(this, "Käfer", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.bug);
                break;

        }
    }
    private void tiles(String value, int cardnumber) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File("solutions.xml"));
        document.getDocumentElement().normalize();
        System.out.println(document.getDocumentElement().getNodeName());
        NodeList nodeList = document.getElementsByTagName("Lion");


        NodeList node = nodeList.item(cardnumber+1).getChildNodes();

        for(int j = 0; j<node.getLength(); j++){
            Node childNode = node.item(j);
            if("Tile".equals(childNode.getNodeName())){

                System.out.println(childNode.getAttributes().getNamedItem("first").getNodeValue());
                System.out.println(childNode.getAttributes().getNamedItem("second").getNodeValue());
                System.out.println(childNode.getAttributes().getNamedItem("third").getNodeValue());

            }

        }
    }
}