from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button
from kivy.core.window import Window

Window.clearcolor = (0.06, 0.09, 0.14, 1)

class LenoApp(App):
    def build(self):
        root = BoxLayout(orientation="vertical", padding=15, spacing=12)

        header = Label(
            text="[b]Leno[/b]\\nپیام‌رسان شما",
            markup=True,
            font_size=28,
            size_hint_y=0.18
        )
        root.add_widget(header)

        self.messages = Label(
            text="به Leno خوش آمدی! 🖤",
            halign="right",
            valign="top"
        )
        self.messages.bind(size=self.messages.setter("text_size"))
        root.add_widget(self.messages)

        self.input = TextInput(
            hint_text="پیامت رو بنویس...",
            multiline=False,
            size_hint_y=0.12
        )
        root.add_widget(self.input)

        send = Button(
            text="ارسال پیام",
            size_hint_y=0.12,
            background_color=(0.35, 0.25, 0.85, 1)
        )
        send.bind(on_press=self.send_message)
        root.add_widget(send)

        return root

    def send_message(self, instance):
        message = self.input.text.strip()
        if message:
            self.messages.text += "\\nشما: " + message
            self.input.text = ""

LenoApp().run()
