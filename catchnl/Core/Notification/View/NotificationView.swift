//
//  NotificationView.swift
//  catchnl
//
//  Created by Fatih Toker on 13.09.2023.
//

import SwiftUI

struct NotificationView: View {
    @EnvironmentObject var contentViewModel: ContentViewModel
    @StateObject var viewModel: NotificationViewModel
    
    init(user: User){
        self._viewModel = StateObject(wrappedValue: NotificationViewModel(user: user))
    }
    
    @State var isSendedOpen = true
    
    
    var body: some View {
        NavigationView {
            VStack{
                HStack{
                    Button {
                        isSendedOpen = true
                    } label: {
                        Text("Verstuurd")
                            .padding()
                            .foregroundColor(isSendedOpen ? .white : Color("primary-color"))
                            .padding(.horizontal, 40)
                            .background(isSendedOpen ? Color("primary-color") : .white)
                            .cornerRadius(10)
                    }
                    
                    Button {
                        isSendedOpen = false
                    } label: {
                        Text("Ontvangen")
                            .padding()
                            .foregroundColor(isSendedOpen ? Color("primary-color") : .white)
                            .padding(.horizontal, 40)
                            .background(isSendedOpen ? .white : Color("primary-color"))
                            .cornerRadius(10)

                            
                    }
                }
                
                Text("JOUW GEREGISTEERDE MELDINGEN")
                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                ScrollView(.vertical){
                    if isSendedOpen {
                        ForEach(viewModel.sendedNotifications, id: \.self){ complaint in
                            HStack{
                                Text(complaint.incident)
                                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                                Text("/")
                                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                                Text(complaint.date)
                                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                                
                            }
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color(.systemGray6))
                            .cornerRadius(10)
                            .padding(.horizontal)
                        }
                    } else {
                        ForEach(viewModel.receivedNotifications, id: \.self){ complaint in
                            HStack{
                                Text(complaint.incident)
                                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                                Text("/")
                                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                                Text(complaint.date)
                                    .font(.custom(Font.Poppins_SemiBold, size: 20))
                                
                            }
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color(.systemGray6))
                            .cornerRadius(10)
                            .padding(.horizontal)
                        }
                    }
                }

                Spacer()
            }
            
            .modifier(TopBarModifier(isFromMain: true))
        }
    }
}

struct NotificationView_Previews: PreviewProvider {
    static var previews: some View {
        NotificationView(user: User())
    }
}
