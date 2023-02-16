package com.weighttracker.screen.bmi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.AppTheme
import com.weighttracker.Screens
import com.weighttracker.browser
import com.weighttracker.component.InputField
import com.weighttracker.component.NumberInputField
import com.weighttracker.navigateTo
import java.text.DecimalFormat

@Composable
fun BmiScreen() {
    val viewModel: BmiViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: BmiState,
    onEvent: (BmiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.quote != null && state.quote.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navigateTo(Screens.Quote(backTo = Screens.BMI))
                        },
                    text = "${state.quote}",
                    color = Color.Magenta,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.width(32.dp))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            MoreMenuButton()
        }

        Spacer(modifier = Modifier.height(0.dp))

        Text(
            text = "Log your weight and height",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberInputField(number = state.weight,
                modifier = Modifier.weight(1f), placeholder = "Weight", onValueChange = {
                    onEvent(BmiEvent.WeightChange(newWeightRec = it))
                })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.clickable {
                    navigateTo(Screens.Settings)
                },
                fontWeight = FontWeight.Bold,
                text = if (state.kg) "kg" else "lb"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    onEvent(BmiEvent.SaveWeightRecord)
                    navigateTo(Screens.WeightRecords(backTo = Screens.BMI))
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF444647)
                )
            ) {
                Text(text = "Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberInputField(number = state.height,
                modifier = Modifier.weight(1f), placeholder = "Height", onValueChange = {
                    onEvent(BmiEvent.HeightChange(newHeightRec = it))
                })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.clickable {
                    navigateTo(Screens.Settings)
                },
                fontWeight = FontWeight.Bold,
                text = if (state.m) "m" else "foot"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Log your activity for today",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            InputField(
                value = state.activity ?: "",
                modifier = Modifier.weight(1f),
                placeholder = "e.g. swimming, tennis, walking 10k steps, gym",
                onValueChange = {
                    onEvent(BmiEvent.ActivityChange(newActivityRec = it))
                })

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    onEvent(BmiEvent.SaveActivityRecord)
                    navigateTo(Screens.ActivityRecords(backTo = Screens.BMI))
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF119917)
                )
            ) {
                Text(text = "Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Log your water consumption for today",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberInputField(
                number = state.water,
                placeholder = "Water: 1, 2, 1.5, ...",
                onValueChange = {
                    onEvent(BmiEvent.WaterChange(newWaterRec = it))
                })

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    onEvent(BmiEvent.SaveWaterRecord)
                    navigateTo(Screens.WaterRecords(backTo = Screens.BMI))
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF0E3C5F)
                )
            ) {
                Text(text = "Save")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        Spacer(modifier = Modifier.height(24.dp))

        if (state.bmi != null && state.bmi > 0) {
            val bmiFormatted = DecimalFormat("###,###.#").format(state.bmi)
            Text(
                text = "Your BMI is $bmiFormatted.",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (state.normalWeightRange != null && state.normalWeightRange.first > 0 &&
            state.normalWeightRange.second > 0
        ) {
            val minWeightFormatted = DecimalFormat("###,###.#")
                .format(state.normalWeightRange.first)
            val maxWeightFormatted = DecimalFormat("###,###.#")
                .format(state.normalWeightRange.second)
            val weightUnit = if (state.kg) "kg" else "lb"

            Text(
                text = "Your normal weight should be in the range $minWeightFormatted - " +
                        "$maxWeightFormatted $weightUnit according to your BMI.",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (state.bmi != null) {
            val info = bmiInfo(state.bmi)

            val browser = browser()

            var expandedInfo by remember {
                mutableStateOf(false)
            }

            Row() {
                Text(
                    modifier = Modifier.clickable {
                        browser.openUri(info.link)
                    },
                    text = info.type,
                    color = info.color,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    if (expandedInfo) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        expandedInfo = !expandedInfo
                    }
                )
            }

            if (expandedInfo) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = info.message,
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

data class BmiInfo(
    val type: String,
    val link: String,
    val color: Color,
    val message: String
)

private fun bmiInfo(bmi: Double): BmiInfo {
    return if (bmi < 18.5) {
        BmiInfo(
            type = "Underweight",
            link = "https://www.healthdirect.gov.au/what-to-do-if-you-are-underweight",
            color = Color(0xFFA7C7E7),
            message = "You may be malnourished and develop compromised immune function," +
                    " respiratory disease, digestive diseases, cancer or osteoporosis." +
                    " You should probably visit a nutritionist to prepare a better diet plan," +
                    " with caloric surplus."
        )
    } else if (bmi in 18.5..25.0) {
        BmiInfo(
            type = "Normal",
            link = "https://www.hioscar.com/blog/how-to-maintain-a-healthy-body-mass-index-bmi",
            color = Color(0xFF4CBB17),
            message = "Your BMI falls within the healthy weight range. Keep it up!"
        )
    } else if (bmi in 25.0..30.0) {
        BmiInfo(
            type = "Overweight",
            link = "https://www.hioscar.com/blog/how-to-maintain-a-healthy-body-mass-index-bmi",
            color = Color(0xFFE2D02B),
            message = "Being overweight might be a sign that your food supply are plentiful and" +
                    " your lifestyle is sedentary. You should probably consider developing a healthier," +
                    " more balanced diet and move more everyday."
        )
    } else if (bmi in 30.0..35.0) {
        BmiInfo(
            type = "Obesity Class 1",
            link = "https://medlineplus.gov/ency/patientinstructions/000348.htm",
            color = Color(0xFFFFA500),
            message = "People with obesity have a higher chance of developing these health problems:\n" +
                    "\n" +
                    "• High blood glucose (sugar) or diabetes;\n" +
                    "• High blood pressure (hypertension);\n" +
                    "• High blood cholesterol and triglycerides (dyslipidemia, or high blood fats);\n" +
                    "• Heart attacks due to coronary heart disease, heart failure, and stroke;\n" +
                    "• Bone and joint problems. More weight puts pressure on the bones and joints." +
                    "This can lead to osteoarthritis, a disease that causes joint pain and stiffness;\n" +
                    "• Sleep apnea or breathing pauses during sleep. This can cause daytime fatigue or" +
                    " sleepiness, poor attention, and problems at work;\n" +
                    "• Gallstones and liver problems;\n" +
                    "• Some cancers."
        )
    } else if (bmi in 35.0..40.0) {
        BmiInfo(
            type = "Obesity Class 2",
            link = "https://medlineplus.gov/ency/patientinstructions/000348.htm",
            color = Color.Red,
            message = "People with obesity have a higher chance of developing these health problems:\n" +
                    "\n" +
                    "• High blood glucose (sugar) or diabetes;\n" +
                    "• High blood pressure (hypertension);\n" +
                    "• High blood cholesterol and triglycerides (dyslipidemia, or high blood fats);\n" +
                    "• Heart attacks due to coronary heart disease, heart failure, and stroke;\n" +
                    "• Bone and joint problems. More weight puts pressure on the bones and joints." +
                    "This can lead to osteoarthritis, a disease that causes joint pain and stiffness;\n" +
                    "• Sleep apnea or breathing pauses during sleep. This can cause daytime fatigue or" +
                    " sleepiness, poor attention, and problems at work;\n" +
                    "• Gallstones and liver problems;\n" +
                    "• Some cancers."
        )
    } else {
        BmiInfo(
            type = "Obesity Class 3 (Severe)",
            link = "https://medlineplus.gov/ency/patientinstructions/000348.htm",
            color = Color(0xFFC41E3A),
            message = "People with obesity have a higher chance of developing these health problems:\n" +
                    "\n" +
                    "• High blood glucose (sugar) or diabetes;\n" +
                    "• High blood pressure (hypertension);\n" +
                    "• High blood cholesterol and triglycerides (dyslipidemia, or high blood fats);\n" +
                    "• Heart attacks due to coronary heart disease, heart failure, and stroke;\n" +
                    "• Bone and joint problems. More weight puts pressure on the bones and joints." +
                    "This can lead to osteoarthritis, a disease that causes joint pain and stiffness;\n" +
                    "• Sleep apnea or breathing pauses during sleep. This can cause daytime fatigue or" +
                    " sleepiness, poor attention, and problems at work;\n" +
                    "• Gallstones and liver problems;\n" +
                    "• Some cancers."
        )
    }
}

@Composable
fun MoreMenuButton(
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            navigateTo(screens = Screens.Settings)
        },
        enabled = true
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = "more")
    }
}


// region Preview (YOUR PREVIEWS BEGIN HERE)
@Preview
@Composable
private fun Preview() {
    AppTheme {
        UI(
            state = BmiState(
                weight = 0.0,
                height = 0.0,
                bmi = 0.0,
                kg = true,
                m = true,
                quote = "",
                normalWeightRange = null,
                activity = "",
                water = null
            ),
            onEvent = {}
        )
    }
}
// endregion