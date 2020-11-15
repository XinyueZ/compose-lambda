package com.example.composelambda.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.composelambda.R
import com.example.composelambda.appNav.AppNavigator


@Composable
fun BuildDetailPage() {
    Scaffold(
        topBar = {
            BuildAppBar(
                "The guy, occupying the Oval"
            ) {
                AppNavigator.popBackStack()
            }
        },
        bodyContent = {
            ScrollableColumn(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.preferredHeight(16.dp))
                Image(
                    imageResource(R.drawable.trump_dump),
                    Modifier
                        .preferredHeight(300.dp)
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(10.dp),
                        ),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.preferredHeight(16.dp))
                Text(
                    text = "The guy, occupying the Oval, won't leave ?",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.preferredHeight(16.dp))
                Text(
                    text = "Former Vice President Joe Biden on Friday vowed to end the \"total, unrelenting, unending warfare\" of Trump-era politics as he relentlessly piled up votes in key states that look increasingly likely to pave his way to presidency.\n" +
                            "\n" +
                            "In a short address to the American people, which he had hoped would be a victory speech before vote counts dragged on, the Democratic nominee professed confidence that his win over President Donald Trump would soon be declared. He called for calm and patience at a moment of flaring national tensions, as Trump warns he will dispute the result of the election if he doesn't win. And he painted a picture of a nascent administration that was already preparing to take on the pandemic and to help revive the economy on its first day in office.\n" +
                            "\"The numbers tell us a clear and convincing story. We are going to win this race,\" Biden said.\n" +
                            "The former vice president spent the day stretching his leads in Pennsylvania, Georgia and Nevada and holding off the President's challenge in Arizona, as vote counting continued and he moved ever closer to the 270 electoral votes needed to win the presidency.\n" +
                            "In the latest batch of results from the Keystone State, Biden expanded his lead over Trump, which is now up to 28,833, meaning the President's already thin hopes of catching up are fast dwindling. Trump cannot win a second term without Pennsylvania, and if Biden captures its 20 electoral votes he cannot be stopped.\n" +
                            "The former vice president is also stretching his leads in Nevada and Georgia, though each state remains too close to call. He leads Trump by more than 22,000 votes in Nevada and is ahead by more than 7,000 in Georgia. The count will be complicated in Pennsylvania by tens of thousands of provisional ballots and many others that require extra care for reasons that include damage, legibility, signature issues or other defects.\n" +
                            "The President cannot reach 270 electoral votes without winning both Pennsylvania and Georgia, and at least one of the other outstanding states. Biden can get over the top by winning Pennsylvania on its own or by taking both Nevada and Arizona. The challenger currently leads the President by 253 to 213 electoral votes, CNN projects.\n" +
                            "Trump is cutting the Democrat's lead in Arizona, which is down to fewer than 30,000 votes with 94% reported, but it is not clear whether his margins are sufficiently wide to overtake his rival with 235,000 votes still to be counted.\n" +
                            "Biden's late-night appearance in Wilmington, Delaware, struck a contrast to Trump's grievance-filled speech the night before, in which he flung false claims that the election is being stolen and vowed to fight on in the courts. Claiming a mandate to ease the nation's angry divides, the former vice president vowed that life in America under a Biden administration would be less angst-ridden and acrimonious, and on the worst day yet for Covid-19 infections -- more than 125,000 -- he offered condolences to those who have lost loved ones.\n" +
                            "\"We have to remember: The purpose of our politics isn't total, unrelenting, unending warfare. No. The purpose of our politics, the work of the nation, isn't to fan the flames of conflict -- but to solve problems,\" Biden said. \"To guarantee justice. To give everybody a fair shot. To improve the lives of our people.\"\n",
                    style = MaterialTheme.typography.body1
                        .copy(
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 0.12.em
                        )

                )
                Spacer(modifier = Modifier.preferredHeight(16.dp))
            }
        }
    )
}
