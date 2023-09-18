//
//  LoginView.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI

struct ProfileView: View {
    @EnvironmentObject var contentViewModel: ContentViewModel
    @StateObject var viewModel = ProfileViewModel()
    
    @State var isUpdated = false


    
    var body: some View {
        NavigationView {
            VStack{
                switch viewModel.state {
                case .nothing:
                    ScrollView(.vertical){
                        Spacer()
                       
                        

                            
                        ZStack(alignment: .center){
                            Image("licencePlate")
                                .resizable()
                                .scaledToFit()
                            
                            TextField("", text: $viewModel.licencePlate)
                                .font(.custom(Font.Poppins_SemiBold, size: 30))
                                .keyboardType(.URL)
                                .textInputAutocapitalization(.characters)
                                .padding(.leading, 70)
                        }
                                            
                        
                        VStack(alignment: .leading){
  
                            
                            InputCellView(hint: "Naam",systemImageName: "person.circle" , text: $viewModel.name, state: .text)
                                .padding(.horizontal)
                            
                            InputCellView(hint: "Email",systemImageName: "envelope" , text: $viewModel.email, state: .text)
                                .padding(.horizontal)
    

                        }
                        .padding(.top)

                        Button {
                            viewModel.state = .loading
                            viewModel.updateUserData{ isSuccess in
                                if isSuccess {
                                    isUpdated = true
                                    viewModel.state = .nothing
                                    contentViewModel.updateProfile()
                                } else {
                                    viewModel.state = .nothing
                                }
                            }
                        } label: {
                            Text("Update")
                                .modifier(ButtonModifier())
                                .font(.custom(Font.Poppins_SemiBold, size: 18))
                        }
                        
                        Button{
                            viewModel.signOut()
                            contentViewModel.checkIfUserExists()
                        } label: {
                            Text("Uitloggen")
                                .font(.custom(Font.Poppins_SemiBold, size: 24))
                                .foregroundColor(Color("primary-color"))
                                .padding(.top)
                        }
                        
          
                        Spacer()
                    }
                    .padding(.horizontal, 5)
                    .alert(viewModel.errorMessage, isPresented: $viewModel.isError) {
                        Button("OK", role: .cancel) { }
                    }
                    .alert("We hebben uw profiel bijgewerkt", isPresented: $isUpdated){
                        Button("OK", role: .cancel) { }
                    }
                    
                case .loading:
                    ProgressView()
                    
                }
            }
            .onAppear{
                contentViewModel.updateProfile()
                viewModel.name = contentViewModel.currentUser.name!
                viewModel.licencePlate = contentViewModel.currentUser.licencePlate!
                viewModel.email = contentViewModel.currentUser.email!
                viewModel.userID = contentViewModel.currentUser.id!
            }
            
            
            .modifier(TopBarModifier(isFromMain: true))
            .alert(viewModel.errorMessage, isPresented: $viewModel.isError) {
                Button("OK", role: .cancel) { }
            }
        }
    
    
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
