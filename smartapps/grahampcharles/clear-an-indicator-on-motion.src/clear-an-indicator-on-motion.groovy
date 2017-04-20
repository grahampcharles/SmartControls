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
    name: "Clear an Indicator on Motion",
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
        		[  0: "Off", 23: "Soft White" , 53: "Daylight" , 20: "Warm White" , 70: "Blue", 39: "Green", 25: "Yellow",10: "Orange", 75: "Purple", 83: "Pink", 100: "Red" ]
    }
     section ("Then set it to...") {
    	input "colorAfter", "enum", title: "What color?", required: true, defaultValue: 0, options:
        		[  0: "Off", 23: "Soft White" , 53: "Daylight" , 20: "Warm White" , 70: "Blue", 39: "Green", 25: "Yellow",10: "Orange", 75: "Purple", 83: "Pink", 100: "Red" ]
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
    doIt = (indicator1.hue == colorBefore);

	if (doIt) {
    	if (colorAfter == 0) {
    		indicator1.setSaturation(0);
        } else {
        	indicator1.setHue(colorAfter);
            switch (colorAfter) {
            	case 23: 
					indicator1.setSaturation(56);
                	break;
                case 53:
                	indicator1.setSaturation(91);
                	break;
                case 52: 
                	indicator1.setSaturation(19);
                	break;
                case 20: 
                	indicator1.setSaturation(80);
					break;                
                default: 
                	indicator1.setSaturation(100);
                    break;
            }
        }
            
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