package com.weighttracker.screen.bmi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
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
import com.weighttracker.component.*
import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.formatBmi
import com.weighttracker.navigateTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        state = listState
    ) {
        item(key = "quote and more menu") {
            QuoteAndMoreMenu(quote = state.quote)
        }

        item(key = "log weight and height") {
            SectionTitle("Log your weight and height")

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "weight input and save") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                WeightInput(
                    weight = state.weight,
                    kgSelected = state.kg,
                    onWeightChange = {
                        onEvent(BmiEvent.WeightChange(newWeightRec = it))
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                SaveButton(
                    onSave = {
                        onEvent(BmiEvent.SaveWeightRecord)
                    },
                    color = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFFE21E45)
                    ),
                    screen = Screens.WeightRecords(backTo = Screens.BMI)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "height input") {
            HeightInputAndSave(
                height = state.height,
                mSelected = state.m,
                onHeightChange = {
                    onEvent(BmiEvent.HeightChange(newHeightRec = it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "log activity") {
            SectionTitle(text = "Log your activity for today")

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "activity input and save") {
            Row() {
                ActivityInput(
                    activity = state.activity,
                    onActivityChange = {
                        onEvent(BmiEvent.ActivityChange(newActivityRec = it))
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                SaveButton(
                    onSave = {
                        onEvent(BmiEvent.SaveActivityRecord)
                    },
                    color = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF30942D)
                    ),
                    screen = Screens.ActivityRecords(backTo = Screens.BMI)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "log water consumption") {
            SectionTitle(text = "Log your water consumption for today")

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "glasses grid") {
            GlassesGrid(glasses = state.glasses, onEvent = onEvent)

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "water input and save") {
            Row() {
                WaterInput(
                    water = state.water,
                    onWaterChange = {
                        onEvent(BmiEvent.WaterChange(newWaterRec = it))
                    },
                    lSelected = state.l
                )

                Spacer(modifier = Modifier.width(16.dp))

                SaveButton(
                    onSave = { onEvent(BmiEvent.SaveWaterRecord) },
                    color = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF0E3C5F)
                    ),
                    screen = Screens.WaterRecords(backTo = Screens.BMI)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "divider") {
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "bmi result") {
            BmiResult(bmi = state.bmi)

            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "normal weight range message") {
            if (state.normalWeightRange != null) {
                NormalWeightRangeMessage(
                    normalWeightRange = state.normalWeightRange,
                    kgSelected = state.kg
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        item(key = "bmi status and message") {
            BmiStatusAndMessage(
                bmi = state.bmi,
                coroutineScope = coroutineScope,
                listState = listState
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BmiStatusAndMessage(
    bmi: Double?,
    coroutineScope: CoroutineScope,
    listState: LazyListState
) {
    if (bmi != null) {
        val info = bmiInfo(bmi)

        val browser = browser()

        var expandedState by remember {
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

            ExpandCollapseArrow(
                expanded = expandedState,
                onExpandChange = { expanded ->
                    expandedState = expanded
                    coroutineScope.launch {
                        if (expanded) {
                            listState.animateScrollToItem(index = 13)
                        }
                    }
                }
            )
        }

        if (expandedState) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = info.message,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun NormalWeightRangeMessage(
    normalWeightRange: NormalWeightRange,
    kgSelected: Boolean
) {
    if (normalWeightRange.minWeight > 0 && normalWeightRange.maxWeight > 0
    ) {
        val minWeightFormatted = DecimalFormat("###,###.#")
            .format(normalWeightRange.minWeight)
        val maxWeightFormatted = DecimalFormat("###,###.#")
            .format(normalWeightRange.maxWeight)
        val weightUnit = if (kgSelected) "kg" else "lb"

        Text(
            text = "Your normal weight should be in the range $minWeightFormatted - " +
                    "$maxWeightFormatted $weightUnit according to your BMI.",
            fontSize = 16.sp
        )
    }
}

@Composable
fun BmiResult(bmi: Double?) {
    if (bmi != null && bmi > 0) {
        Text(
            modifier = Modifier.clickable {
                navigateTo(Screens.WeightRecords(backTo = Screens.BMI))
            },
            text = "Your BMI is ${formatBmi(bmi)}.",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun WaterInput(
    water: Double?,
    onWaterChange: (Double) -> Unit,
    lSelected: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NumberInputField(
            modifier = Modifier.width(90.dp),
            number = water,
            placeholder = "1.5, ...",
            onValueChange = {
                onWaterChange(it)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = if (lSelected) "l" else "gal",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RowScope.ActivityInput(
    activity: String?,
    onActivityChange: (String) -> Unit
) {
    InputField(
        value = activity ?: "",
        modifier = Modifier.weight(1f),
        placeholder = "e.g. swimming, tennis, walking 10k steps, gym",
        onValueChange = {
            onActivityChange(it)
        }
    )
}

@Composable
fun HeightInputAndSave(
    height: Double?,
    mSelected: Boolean,
    onHeightChange: (Double) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NumberInputField(
            modifier = Modifier.width(90.dp),
            number = height,
            placeholder = "Height",
            onValueChange = {
                onHeightChange(it)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.clickable {
                navigateTo(Screens.Settings)
            },
            fontWeight = FontWeight.Bold,
            text = if (mSelected) "m" else "foot"
        )
    }
}

@Composable
fun SaveButton(onSave: () -> Unit, color: ButtonColors, screen: Screens) {
    Button(
        colors = color,
        onClick = {
            onSave()
            navigateTo(screen)
        }
    ) {
        Text(text = "Save")
    }
}

@Composable
fun WeightInput(
    weight: Double?,
    kgSelected: Boolean,
    onWeightChange: (Double) -> Unit
) {
    NumberInputField(
        modifier = Modifier.width(90.dp),
        number = weight,
        placeholder = "Weight",
        onValueChange = {
            onWeightChange(it)
        }
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text(
        modifier = Modifier.clickable {
            navigateTo(Screens.Settings)
        },
        fontWeight = FontWeight.Bold,
        text = if (kgSelected) "kg" else "lb"
    )
}

@Composable
fun QuoteAndMoreMenu(quote: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (quote != null && quote.isNotBlank()) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navigateTo(Screens.Quote(backTo = Screens.BMI))
                    },
                text = "$quote",
                color = Color(0xFFFF5722),
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.width(32.dp))
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        MoreMenuButton()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GlassesGrid(glasses: List<Boolean>, onEvent: (BmiEvent) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        glasses.forEach { filled ->
            Glass(
                modifier = Modifier.padding(vertical = 8.dp),
                filled = filled,
                onFilledChange = { onEvent(BmiEvent.GlassClick(filled = filled)) }
            )
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
    return if (bmi < 18.49) {
        BmiInfo(
            type = "Underweight",
            link = "https://www.healthdirect.gov.au/what-to-do-if-you-are-underweight",
            color = Color(0xFFA7C7E7),
            message = "You may be malnourished and develop compromised immune function," +
                    " respiratory disease, digestive diseases, cancer or osteoporosis." +
                    " You should probably visit a nutritionist to prepare a better diet plan," +
                    " with caloric surplus."
        )
    } else if (bmi in 18.49..25.0) {
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
            color = Color(0xFFD6C209),
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
                water = 0.0,
                l = true,
                glasses = emptyList()
            ),
            onEvent = {}
        )
    }
}
// endregion