/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Lights Off, When Closed
 *
 *  Author: SmartThings
 */
definition(
    name: "Clear an Indicator On Motion",
    namespace: "grahampcharles",
    author: "Graham Charles",
    description: "Turn off an indicator light when a motion detector triggers, but only if the indicator is already a specific color.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet@2x.png"
)

preferences {
	section ("When motion is detected...") {
		input "motion1", "capability.motionSensor", title: "Where?", required: true
	}
    section ("And the indicator light...") {
    	input "indicator1", "capability.colorControl", title: "Which light?", required: true
    }
     section ("Is already set to...") {
    	input "colorBefore", "enum", title: "What color?", required: true, options:
        		[  "Soft White" , "Daylight" , "White", "Warm White" , "Blue", "Green", "Yellow", "Orange", "Purple", "Pink", "Red" ]
    }
	section ("Then turn the light...") {
		input "lightOn", "bool", defaultValue: false, required: true
	}
}

def installed()
{
	subscribe(contact1, "motion1.active", motionDetectorHandler)
}

def updated()
{
	unsubscribe()
	subscribe(contact1, "motion1.active", motionDetectorHandler)
}

def motionDetectorHandler(evt) {
	// get expected hue
    var doIt = false;
    
    // get hue
    switch (indicator1.hue) {
    	case 23: doIt = (colorBefore == "Soft White");
        	break;
    	case 53: doIt = (colorBefore == "Daylight");
        	break;    	
    	case 20: doIt = (colorBefore == "Warm White");
        	break;
    	case 70: doIt = (colorBefore == "Blue");
        	break;
    	case 39: doIt = (colorBefore == "Green");
        	break;
    	case 25: doIt = (colorBefore == "Yellow");
        	break;
    	case 10: doIt = (colorBefore == "Orange");
        	break;
    	case 75: doIt = (colorBefore == "Purple");
        	break;
    	case 83: doIt = (colorBefore == "Pink");
        	break;
    	case 100: doIt = (colorBefore == "Red");
        	break;
        default: 
	        break;
    }
    
	if (doIt) {
    	indicator1.setSaturation(0);
    }
	
    /*
    final colors = [
        [name:"Soft White", hue: 23, saturation: 56],
        [name:"Daylight", hue: 53, saturation: 91],
        [name:"White", hue: 52, saturation: 19],
        [name:"Warm White", hue: 20, saturation: 80],
        [name:"Blue", hue: 70, saturation: 100],
        [name:"Green", hue: 39, saturation: 100],
        [name:"Yellow", hue: 25, saturation: 100],
        [name:"Orange", hue: 10, saturation: 100],
        [name:"Purple", hue: 75, saturation: 100],
        [name:"Pink", hue: 83, saturation: 100],
        [name:"Red", hue: 100, saturation: 100]
	]
    */
}